/**************************************************************/
/*                                                            */ 
/* CFacebookActivity.java                                     */ 
/* (c)2014 jmarcosf                                           */ 
/*                                                            */ 
/* Description: CFacebookActivity Class                       */ 
/*              Práctica asignatura Android Avanzado          */ 
/*              U-Tad - Master Apps                           */ 
/*              www.u-tad.com                                 */ 
/*                                                            */ 
/*        Date: January 2014                                  */ 
/*                                                            */ 
/**************************************************************/
package com.utad.marcos.jorge.practicaandroidavanzado;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.Session.StatusCallback;
import com.facebook.SessionState;

/**************************************************************/
/*                                                            */ 
/*                                                            */ 
/*                                                            */ 
/* CFacebookActivity Class                                    */ 
/*                                                            */ 
/*                                                            */ 
/*                                                            */ 
/**************************************************************/
public class CFacebookActivity extends CBaseActivity implements Session.StatusCallback
{
public  static final String   IDS_PICTURE_PARAM                  = "PictureParam";
public  static final String   IDS_DEGREES_PARAM                  = "DegreesParam";
public  static final String   IDS_DIRECTION_PARAM                = "DirectionParam";
public  static final String   IDS_LOCATION_PARAM                 = "LocationParam";
private static final String   IDS_FACEBOOK_PUBLISH_PERMISSION    = "publish_actions";

public  static final int      RESULT_ERROR_CONNECTING            = -11;
public  static final int      RESULT_ERROR_LOGGING_ON            = -12;
public  static final int      RESULT_ERROR_ACQUIRING_PERMISSIONS = -13;
public  static final int      RESULT_ERROR_PUBLISHING_INFO       = -14;
public  static final int      RESULT_ERROR_PUBLISHING_PICTURE    = -15;
public  static final int      RESULT_ERROR_OTHER                 = -16;

private Bitmap      m_Picture      = null;
private int         m_Degrees      = 0;
private String      m_Direction    = "";
private String      m_Location     = null;
private AlertDialog m_Dialog       = null;

     /*********************************************************/
     /*                                                       */ 
     /*                                                       */ 
     /* Activity Override Methods                             */ 
     /*                                                       */ 
     /*                                                       */ 
     /*********************************************************/
     /*                                                       */ 
     /* CFacebookActivity.onCreate()                          */ 
     /*                                                       */ 
     /*********************************************************/
     @Override
     protected void onCreate( Bundle savedInstanceState )
     {
          super.onCreate( savedInstanceState );
          setContentView( R.layout.layout_activity_facebook );

          Bundle Params = getIntent().getExtras();
          byte[] byteArray = Params.getByteArray( IDS_PICTURE_PARAM );
          m_Picture = BitmapFactory.decodeByteArray( byteArray, 0, byteArray.length );
          m_Degrees = Params.getInt( IDS_DEGREES_PARAM, 0 );
          m_Direction = Params.getString( IDS_DIRECTION_PARAM, "" );
          m_Location = Params.getString( IDS_LOCATION_PARAM, "" );
          
          AlertDialog.Builder builder = new AlertDialog.Builder( this );
          builder.setTitle( R.string.IDS_POST_TO_FACEBOOK_TITLE );
          builder.setMessage( R.string.IDS_POSTING_TO_FACEBOOK_MESSAGE );
          builder.setIcon( R.drawable.image_facebook );
          m_Dialog = builder.create();
          
          Session session = Session.getActiveSession();
          if( session == null )
          {
               if( savedInstanceState != null ) session = Session.restoreSession( this, null, this, savedInstanceState );
               if( session == null ) session = new Session( this );
               Session.setActiveSession( session );
               if( session.getState().equals( SessionState.CREATED_TOKEN_LOADED ) )
               {
                    session.openForRead( new Session.OpenRequest( this ).setCallback( this ) );
               }
          }
     }

     /*********************************************************/
     /*                                                       */ 
     /* CFacebookActivity.onStart()                           */ 
     /*                                                       */ 
     /*********************************************************/
     @Override
     protected void onStart()
     {
          super.onStart();
          Session.getActiveSession().addCallback( this );
          Post();
     }
          
     /*********************************************************/
     /*                                                       */ 
     /* CFacebookActivity.onActivityResult()                  */ 
     /*                                                       */ 
     /*********************************************************/
     @Override
     protected void onActivityResult( int requestCode, int resultCode, Intent data )
     {
          super.onActivityResult( requestCode, resultCode, data );
          Session.getActiveSession().onActivityResult( this, requestCode, resultCode, data );
          if( requestCode == 64206 && resultCode == 0 ) this.finish();
     }

     /*********************************************************/
     /*                                                       */ 
     /* CFacebookActivity.onStop()                            */ 
     /*                                                       */ 
     /*********************************************************/
     @Override
     protected void onStop()
     {
          super.onStop();
          Session.getActiveSession().removeCallback( this );
     }
          
     /*********************************************************/
     /*                                                       */ 
     /* CFacebookActivity.onSaveInstanceState()               */ 
     /*                                                       */ 
     /*********************************************************/
     @Override
     protected void onSaveInstanceState( Bundle outState )
     {
          super.onSaveInstanceState( outState );
          Session.saveSession( Session.getActiveSession(), outState );
     }

     /*********************************************************/
     /*                                                       */ 
     /*                                                       */ 
     /* Session.StatusCallback Interface Methods              */ 
     /*                                                       */ 
     /*                                                       */ 
     /*********************************************************/
     /*                                                       */ 
     /* CFacebookActivity.call()                              */ 
     /*                                                       */ 
     /*********************************************************/
     @Override
     public void call( Session session, SessionState state, Exception exception )
     {
          if( exception != null )
          {
               m_Dialog.dismiss();
               new AlertDialog.Builder( this )
                    .setTitle( R.string.IDS_POST_TO_FACEBOOK_TITLE )
                    .setMessage( "Exception Error: " + exception.getMessage() )
                    .setIcon( android.R.drawable.ic_dialog_alert )
                    .setPositiveButton( android.R.string.ok, new DialogInterface.OnClickListener()
                    {
                         @Override
                         public void onClick( DialogInterface dialogInterface, int i )
                         {
                              setResult( RESULT_ERROR_OTHER );
                              finish();
                         }
                    } )
                    .show();
          }
          else switch( state )
          {
               case OPENED:
                    Log.d( CFacebookActivity.class.getSimpleName(), "Session opened!" );
                    break;
               
               case OPENING:
                    Log.d( CFacebookActivity.class.getSimpleName(), "Session opening!" );
                    break;
                    
               case CREATED:
                    Log.d( CFacebookActivity.class.getSimpleName(), "Session created!" );
                    break;
                    
               case CLOSED:
                    Log.d( CFacebookActivity.class.getSimpleName(), "Session closed!" );
                    break;
                    
               case CLOSED_LOGIN_FAILED:
                    Log.d( CFacebookActivity.class.getSimpleName(), "Session closed. Login failed!" );
                    break;
                    
               case CREATED_TOKEN_LOADED:
                    Log.d( CFacebookActivity.class.getSimpleName(), "Session created. Token loaded!" );
                    break;
                    
               case OPENED_TOKEN_UPDATED:
                    Log.d( CFacebookActivity.class.getSimpleName(), "Session opened. Token updated!" );
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
     /* CFacebookActivity.Post()                              */ 
     /*                                                       */ 
     /*********************************************************/
     private void Post()
     {
          Session session = Session.getActiveSession();
          if( session.isOpened() )
          {
               m_Dialog.show();
               doPublish();
          }
          else doLogin();
     }
     
     /*********************************************************/
     /*                                                       */ 
     /* CFacebookActivity.doLogin()                           */ 
     /*                                                       */ 
     /*********************************************************/
     private void doLogin()
     {
          Session session = Session.getActiveSession();
          if( !session.isOpened() && !session.isClosed() )
          {
               session.openForRead( new Session.OpenRequest( this ).setCallback( this ) );
          }
          else
          {
               Session.openActiveSession( this, true, this );
          }
     }
     
     /*********************************************************/
     /*                                                       */ 
     /* CFacebookActivity.doLogout()                          */ 
     /*                                                       */ 
     /*********************************************************/
     private void doLogout( final int resultCode, String ErrorMessage )
     {
          SendEvent( GA_EVENT_CATEGORY_POST_ACTION, GA_EVENT_ACTION_POST_IMAGE, ( resultCode == RESULT_OK ) ? "" : ErrorMessage, (long)resultCode );
          
          m_Dialog.dismiss();
          Session session = Session.getActiveSession();
          if( !session.isClosed() ) session.closeAndClearTokenInformation();
          if( resultCode != RESULT_OK )
          {
               Toast.makeText( CFacebookActivity.this, "Error %d: " + ErrorMessage, Toast.LENGTH_LONG).show();
               Log.e( CFacebookActivity.class.getSimpleName(), "Error %d: " + ErrorMessage );
               
          }
          
          String Message = null;
          if( resultCode == RESULT_OK ) Message = getString( R.string.IDS_POST_TO_FACEBOOK_SUCCESS );
          else Message = "Error: " + resultCode + ( ( ErrorMessage == null ) ? "" : ErrorMessage );
          
          new AlertDialog.Builder( this )
               .setTitle( R.string.IDS_POST_TO_FACEBOOK_TITLE )
               .setMessage( Message )
               .setIcon( (resultCode == RESULT_OK ) ? R.drawable.image_facebook : android.R.drawable.ic_dialog_alert )
               .setPositiveButton( android.R.string.ok, new DialogInterface.OnClickListener()
               {
                    @Override
                    public void onClick( DialogInterface dialogInterface, int i )
                    {
                         setResult( resultCode );
                         finish();
                    }
               } )
               .show();
     }
     
     /*********************************************************/
     /*                                                       */ 
     /* CFacebookActivity.doPublish()                         */ 
     /*                                                       */ 
     /*********************************************************/
     private void doPublish()
     {
          Session session = Session.getActiveSession();
          if( !session.getPermissions().contains( IDS_FACEBOOK_PUBLISH_PERMISSION ) )
          {
               m_Dialog.setMessage( getString( R.string.IDS_CONNECTING_TO_FACEBOOK_MESSAGE ) );
               Session.NewPermissionsRequest request = new Session.NewPermissionsRequest( this, IDS_FACEBOOK_PUBLISH_PERMISSION );
               request.setCallback( new StatusCallback()
               {
                    @Override
                    public void call( Session session, SessionState state, Exception exception )
                    {
                         if( session.isOpened() )
                         {
                              doPublish();
                         }
                         else if( exception != null )
                         {
                              doLogout( RESULT_ERROR_ACQUIRING_PERMISSIONS, exception.getMessage() );
                         }
                    }
               } );

               session.requestNewPublishPermissions( request );
          }
//        else doPublishMessage();
          else doPublishPictureEx();
     }

     /*********************************************************/
     /*                                                       */ 
     /* CFacebookActivity.doPublishMessage()                 */ 
     /*                                                       */ 
     /*********************************************************/
     private void doPublishMessage()
     {
          m_Dialog.setMessage( getString( R.string.IDS_POSTING_INFO_MESSAGE ) );
          String Message = "Picture Info:\n" + m_Location + "\n" + m_Degrees + "º " + m_Direction;

          Request requestMessage = Request.newStatusUpdateRequest( Session.getActiveSession(), Message, new Request.Callback()
          {
               @Override
               public void onCompleted( Response response )
               {
                    if( response.getError() == null )
                    {
                         doPublishPicture();
                    }
                    else
                    {
                         doLogout( RESULT_ERROR_PUBLISHING_INFO, response.getError().getErrorMessage() );
                    }
               }
          } );
          
          requestMessage.executeAsync();
     }

     /*********************************************************/
     /*                                                       */ 
     /* CFacebookActivity.doPublishPicture()                  */ 
     /*                                                       */ 
     /*********************************************************/
     private void doPublishPicture()
     {
          m_Dialog.setMessage( getString( R.string.IDS_POSTING_PICTURE_MESSAGE ) );
          Request requestPicture = Request.newUploadPhotoRequest( Session.getActiveSession(), m_Picture, new Request.Callback()
          {
               @Override
               public void onCompleted( Response response )
               {
                    if( response.getError() == null )
                    {
                         doLogout( RESULT_OK, null );
                    }
                    else
                    {
                         doLogout( RESULT_ERROR_PUBLISHING_PICTURE, response.getError().getErrorMessage() );
                    }
               }
          } );
          
          requestPicture.executeAsync();
     }
     
     /*********************************************************/
     /*                                                       */ 
     /* CFacebookActivity.doPublishPictureEx()                */ 
     /*                                                       */ 
     /*********************************************************/
     private void doPublishPictureEx()
     {
          m_Dialog.setMessage( getString( R.string.IDS_POSTING_PICTURE_AND_INFO ) );
          String Message = "Picture Info:\n" + m_Location + "\n" + m_Degrees + "º " + m_Direction;
          
          Request requestPicture = CMyUploadPhotoRequest.newMyUploadPhotoRequest( Session.getActiveSession(), m_Picture, Message, "", new Request.Callback()
          {
               @Override
               public void onCompleted( Response response )
               {
                    if( response.getError() == null )
                    {
                         doLogout( RESULT_OK, null );
                    }
                    else
                    {
                         doLogout( RESULT_ERROR_PUBLISHING_PICTURE, response.getError().getErrorMessage() );
                    }
               }
          } );
          
          requestPicture.executeAsync();
     }
}
