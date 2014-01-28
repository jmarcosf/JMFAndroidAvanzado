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

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
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
          return "TODO: SetLocationInfo!";
     }
}
