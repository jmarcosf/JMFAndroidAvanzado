/**************************************************************/
/*                                                            */ 
/* CMainActivity.java                                         */ 
/* (c)2014 jmarcosf                                           */ 
/*                                                            */ 
/* Description: CMainActivity Class                           */ 
/*              Práctica asignatura Android Avanzado          */ 
/*              U-Tad - Master Apps                           */ 
/*              www.u-tad.com                                 */ 
/*                                                            */ 
/*        Date: January 2014                                  */ 
/*                                                            */ 
/**************************************************************/
package com.utad.marcos.jorge.practicaandroidavanzado;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**************************************************************/
/*                                                            */ 
/*                                                            */ 
/*                                                            */ 
/* CMainActivity Class                                        */ 
/*                                                            */ 
/*                                                            */ 
/*                                                            */ 
/**************************************************************/
public class CMainActivity extends android.support.v4.app.FragmentActivity implements OnClickListener, CShakeDetector.OnShakeListener, SensorEventListener
{
private static final int CAPTURE_PICTURE_REQUEST_CODE = 100;

private Button           m_CapturePicture    = null;
private ImageView        m_Image             = null;
private TextView         m_LocationInfo      = null;
private TextView         m_Degrees           = null;
private GoogleMap        m_Map               = null;
private ImageView        m_Compass           = null;
private Button           m_DismissPicture    = null;
private Button           m_PublishToFacebook = null;

private LocationManager  m_LocationManager   = null;
private SensorManager    m_SensorManager     = null;
private Sensor           m_Accelerometer     = null;
private CShakeDetector   m_ShakeDetector     = null;

private Location         m_CurrentLocation   = null;
private float            m_CurrentDegree     = 0f;
private String           m_CompassDirection  = "";

     /*********************************************************/
     /*                                                       */ 
     /*                                                       */ 
     /* Activity Override Methods                             */ 
     /*                                                       */ 
     /*                                                       */ 
     /*********************************************************/
     /*                                                       */ 
     /* CMainActivity.onCreate()                              */ 
     /*                                                       */ 
     /*********************************************************/
     @Override
     protected void onCreate( Bundle savedInstanceState )
     {
          super.onCreate( savedInstanceState );
          setContentView( R.layout.layout_activity_main );

          m_CapturePicture = (Button)findViewById( R.id.IDC_BTN_CAPTURE_PICTURE );
          m_Image = (ImageView)findViewById( R.id.IDC_IMG_PREVIEW );
          m_LocationInfo = (TextView)findViewById( R.id.IDC_TXT_LOCATION );
          m_Degrees = (TextView)findViewById( R.id.IDC_TXT_DEGREES );
          m_Map = ( (SupportMapFragment)getSupportFragmentManager().findFragmentById( R.id.IDC_MAP_LOCATIONMAP ) ).getMap();

          m_Compass = (ImageView)findViewById( R.id.IDC_IMG_COMPASS );
          m_DismissPicture = (Button)findViewById( R.id.IDC_BTN_DISMISS );
          m_PublishToFacebook = (Button)findViewById( R.id.IDC_BTN_PUBLISH_TO_FACEBOOK );

          m_CapturePicture.setOnClickListener( this );
          m_DismissPicture.setOnClickListener( this );
          m_PublishToFacebook.setOnClickListener( this );
          
          m_LocationManager = (LocationManager)getSystemService( Context.LOCATION_SERVICE );
          
          m_SensorManager = (SensorManager)getSystemService( Context.SENSOR_SERVICE );
          m_Accelerometer = m_SensorManager.getDefaultSensor( Sensor.TYPE_ACCELEROMETER );
          m_ShakeDetector = new CShakeDetector();
          m_ShakeDetector.setOnShakeListener( this );
          
          SetControls( false );
     }

     /*********************************************************/
     /*                                                       */ 
     /* CMainActivity.onResume()                              */ 
     /*                                                       */ 
     /*********************************************************/
     @Override
     public void onResume()
     {
          super.onResume();
          m_SensorManager.registerListener( m_ShakeDetector, m_Accelerometer, SensorManager.SENSOR_DELAY_UI );
          m_SensorManager.registerListener( this, m_SensorManager.getDefaultSensor( Sensor.TYPE_ORIENTATION ), SensorManager.SENSOR_DELAY_GAME );
     }
  
     /*********************************************************/
     /*                                                       */ 
     /* CMainActivity.onPause()                               */ 
     /*                                                       */ 
     /*********************************************************/
     @Override
     public void onPause()
     {
          m_SensorManager.unregisterListener( this );
          m_SensorManager.unregisterListener( m_ShakeDetector );
          super.onPause();
     }
     
     /*********************************************************/
     /*                                                       */ 
     /* CMainActivity.onCreateOptionsMenu()                   */ 
     /*                                                       */ 
     /*********************************************************/
     @Override
     public boolean onCreateOptionsMenu( Menu menu )
     {
          getMenuInflater().inflate( R.menu.menu_main, menu );
          return true;
     }

     /*********************************************************/
     /*                                                       */ 
     /* CMainActivity.onActivityResult()                      */ 
     /*                                                       */ 
     /*********************************************************/
     @Override
     protected void onActivityResult (int requestCode, int resultCode, Intent data )
     {
          if( requestCode == CAPTURE_PICTURE_REQUEST_CODE )
          {
               switch( resultCode )
               {
                    case RESULT_OK:
                         Bundle Params = data.getExtras();
                         Bitmap bitmap = (Bitmap)Params.get( "data" );
                         m_Image.setImageBitmap( bitmap );
                         SetControls( true );
                         break;
                         
                    case RESULT_CANCELED:
                         SetControls( false );
                         break;
                         
                    default:
                         //TODO: Show Error Dialog
                         break;
               }
          }
     }

     /*********************************************************/
     /*                                                       */ 
     /*                                                       */ 
     /* OnClickListener Interface Methods                     */ 
     /*                                                       */ 
     /*                                                       */ 
     /*********************************************************/
     /*                                                       */ 
     /* CMainActivity.onClick()                               */ 
     /*                                                       */ 
     /*********************************************************/
     @Override
     public void onClick( View view )
     {
          switch( view.getId() )
          {
               case R.id.IDC_BTN_CAPTURE_PICTURE:
                    Intent intent = new Intent( MediaStore.ACTION_IMAGE_CAPTURE );
                    startActivityForResult( intent, CAPTURE_PICTURE_REQUEST_CODE );
                    break;
                    
               case R.id.IDC_BTN_DISMISS:
                    SetControls( false );
                    break;
                    
               case R.id.IDC_BTN_PUBLISH_TO_FACEBOOK:
                    break;
          }
     }
          
     /*********************************************************/
     /*                                                       */ 
     /*                                                       */ 
     /* CShakeDetector.OnShakeListener Interface Methods      */ 
     /*                                                       */ 
     /*                                                       */ 
     /*********************************************************/
     /*                                                       */ 
     /* CMainActivity.OnShake()                               */ 
     /*                                                       */ 
     /*********************************************************/
     @Override
     public void OnShake( int count )
     {
          SetControls( false );
     }

     /*********************************************************/
     /*                                                       */ 
     /* CMainActivity.onAccuracyChanged()                     */ 
     /*                                                       */ 
     /*********************************************************/
     @Override
     public void onAccuracyChanged( Sensor arg0, int arg1 )
     {
     }

     /*********************************************************/
     /*                                                       */ 
     /*                                                       */ 
     /* SensorEventListener Interface Methods                 */ 
     /*                                                       */ 
     /*                                                       */ 
     /*********************************************************/
     /*                                                       */ 
     /* CMainActivity.onSensorChanged()                       */ 
     /*                                                       */ 
     /*********************************************************/
     @Override
     public void onSensorChanged( SensorEvent event )
     {
          // get the angle around the z-axis rotated
          float degree = Math.round( event.values[ 0 ] );

          SetCompassDirection( degree );
          m_Degrees.setText( "Heading: " + Float.toString( degree ) + " degrees\n" + m_CompassDirection );

          // create a rotation animation (reverse turn degree degrees)
          RotateAnimation rotateAnimation = new RotateAnimation( m_CurrentDegree, -degree, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f );

          // how long the animation will take place
          rotateAnimation.setDuration(210);

          // set the animation after the end of the reservation status
          rotateAnimation.setFillAfter(true);

          // Start the animation
          m_Compass.startAnimation( rotateAnimation );
          m_CurrentDegree = -degree;
     }

     /*********************************************************/
     /*                                                       */ 
     /*                                                       */ 
     /* Class Methods                                         */ 
     /*                                                       */ 
     /*                                                       */ 
     /*********************************************************/
     /*                                                       */ 
     /* CMainActivity.SetControls()                           */ 
     /*                                                       */ 
     /*********************************************************/
     public void SetControls( boolean bReady )
     {
          m_CapturePicture.setEnabled( !bReady );
          if( !bReady ) m_Image.setImageBitmap( null );
          m_LocationInfo.setText( ( bReady ) ? GetLocationInfo() : "" );
          if( !bReady ) m_Map.clear();
          m_DismissPicture.setEnabled( bReady );
          m_PublishToFacebook.setEnabled( bReady );
     }
     
     /*********************************************************/
     /*                                                       */ 
     /* CMainActivity.GetLocationInfo()                       */ 
     /*                                                       */ 
     /*********************************************************/
     public String GetLocationInfo()
     {
     String LocationInfo = null;
     
          if( m_LocationManager.isProviderEnabled( LocationManager.GPS_PROVIDER ) )
          {
               m_CurrentLocation = m_LocationManager.getLastKnownLocation( LocationManager.GPS_PROVIDER );
               LocationInfo = "Latitude: " + m_CurrentLocation.getLatitude() + "\r\nLongitude: " + m_CurrentLocation.getLatitude();
               new CFindLocationInfo().execute( m_CurrentLocation );
          }

          return LocationInfo;
     }
     
     /*********************************************************/
     /*                                                       */ 
     /* CMainActivity.SetCompassDirection()                   */ 
     /*                                                       */ 
     /*********************************************************/
     public void SetCompassDirection( float Degrees )
     {
          int DirectionIds[] = { R.string.IDS_NORTH, R.string.IDS_NORTH_EAST, R.string.IDS_EAST,
                                 R.string.IDS_SOUTH_EAST, R.string.IDS_SOUTH, R.string.IDS_SOUTH_WEST,
                                 R.string.IDS_WEST, R.string.IDS_NORTH_WEST, R.string.IDS_NORTH };
          int Index = (int)Math.round( ( ( (double)Degrees % 360 ) / 45) );
          m_CompassDirection = getString( DirectionIds[ Index ] );
     }
     
     /*********************************************************/
     /*                                                       */
     /*                                                       */
     /* CManiActivity.CFindLocationInfo AsyncTask Class       */
     /*                                                       */
     /*                                                       */
     /*********************************************************/
     private class CFindLocationInfo extends AsyncTask< Location, Void, String>
     {
          
          /****************************************************/
          /*                                                  */
          /* CFindLocationInfo.doInBackground()               */
          /*                                                  */
          /****************************************************/
          @Override
          protected String doInBackground( Location... Params )
          {
               String    LocationString = null;

               Geocoder geocoder = new Geocoder( CMainActivity.this, Locale.getDefault() );

               Location CurrentLocation = Params[ 0 ];
               List< Address > Addresses = null;
               try
               {
                    Addresses = geocoder.getFromLocation( CurrentLocation.getLatitude(), CurrentLocation.getLongitude(), 1 );
               }
               catch( IOException e ) {}

               if( Addresses != null && Addresses.size() > 0 )
               {
                    Address address = Addresses.get( 0 );
                    // Format the first line of address (if available), city, and country name.
                    LocationString = String.format("%s, %s, %s",
                         address.getMaxAddressLineIndex() > 0 ? address.getAddressLine( 0 ) : "",
                         address.getLocality(),
                         address.getCountryName() );
               }
               return LocationString;
          }
          
          /****************************************************/
          /*                                                  */
          /* CFindLocationInfo.onPostExecute()                */
          /*                                                  */
          /****************************************************/
          @Override
          protected void onPostExecute( String LocationInfo )
          {
               if( LocationInfo != null )
               {
                    m_LocationInfo.append( "\r\n" + LocationInfo );
                    LatLng Position = new LatLng( m_CurrentLocation.getLatitude(), m_CurrentLocation.getLongitude() );
//                    CameraPosition CameraPosition = new CameraPosition.Builder()
//                         .target( Position )
//                         .zoom( 15.5f )
//                         .bearing( 0 )
//                         .tilt( 25 )
//                         .build();
                    m_Map.addMarker( new MarkerOptions().position( Position ).title( "Marker" ) );
                    m_Map.moveCamera( CameraUpdateFactory.newLatLngZoom( Position, 10) );
               }
               else
               {
                    m_LocationInfo.append( "\r\n" + "Error reading Location Info" );
               }
          }
      }
}
