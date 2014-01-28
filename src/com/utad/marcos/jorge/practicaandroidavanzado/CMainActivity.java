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

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**************************************************************/
/*                                                            */ 
/*                                                            */ 
/*                                                            */ 
/* CMainActivity Class                                        */ 
/*                                                            */ 
/*                                                            */ 
/*                                                            */ 
/**************************************************************/
public class CMainActivity extends Activity implements OnClickListener
{
private static final int CAPTURE_PICTURE_REQUEST_CODE = 100;

private Button           m_CapturePicture    = null;
private ImageView        m_Image             = null;
private TextView         m_LocationInfo      = null;
private Button           m_DismissPicture    = null;
private Button           m_PublishToFacebook = null;
private LocationManager  m_LocationManager   = null;

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
          m_DismissPicture = (Button)findViewById( R.id.IDC_BTN_DISMISS );
          m_PublishToFacebook = (Button)findViewById( R.id.IDC_BTN_PUBLISH_TO_FACEBOOK );

          m_CapturePicture.setOnClickListener( this );
          m_DismissPicture.setOnClickListener( this );
          m_PublishToFacebook.setOnClickListener( this );
          
          m_LocationManager = (LocationManager)getSystemService( Context.LOCATION_SERVICE );

          SetControls( false );
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
     
          Location CurrentLocation = null;
          if( m_LocationManager.isProviderEnabled( LocationManager.GPS_PROVIDER ) )
          {
               CurrentLocation = m_LocationManager.getLastKnownLocation( LocationManager.GPS_PROVIDER );
               LocationInfo = "Latitude: " + CurrentLocation.getLatitude() + "\r\nLongitude: " + CurrentLocation.getLatitude();
               new CFindLocationInfo().execute( CurrentLocation );
          }

          return LocationInfo;
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
               }
               else
               {
                    m_LocationInfo.append( "\r\n" + "Error reading Location Info" );
               }
          }
      }

}
