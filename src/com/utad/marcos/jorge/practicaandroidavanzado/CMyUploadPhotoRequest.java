/**************************************************************/
/*                                                            */ 
/* CMyUploadPhotoRequest.java                                 */ 
/* (c)2014 jmarcosf                                           */ 
/*                                                            */ 
/* Description: CMyUploadPhotoRequest Class                   */ 
/*              Práctica asignatura Android Avanzado          */ 
/*              U-Tad - Master Apps                           */ 
/*              www.u-tad.com                                 */ 
/*                                                            */ 
/*        Date: January 2014                                  */ 
/*                                                            */ 
/**************************************************************/
/* NOTE:                                                      */ 
/* This is because FBSDK "apparently2 doesn't provide a way   */
/* to post an image with text in the same Request.            */ 
/*                                                            */ 
/**************************************************************/
package com.utad.marcos.jorge.practicaandroidavanzado;

import android.graphics.Bitmap;
import android.os.Bundle;

import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Session;

/**************************************************************/
/*                                                            */
/*                                                            */
/*                                                            */
/* CMyUploadPhotoRequest Class                                */
/*                                                            */
/*                                                            */
/*                                                            */
/**************************************************************/
public class CMyUploadPhotoRequest extends Request
{
private static final String MY_PHOTOS = "me/photos";
private static final String PICTURE_PARAM = "picture";
     
     /*********************************************************/
     /*                                                       */ 
     /*                                                       */ 
     /* Class Methods                                         */ 
     /*                                                       */ 
     /*                                                       */ 
     /*********************************************************/
     /*                                                       */ 
     /* CMyUploadPhotoRequest.newMyUploadPhotoRequest()       */ 
     /*                                                       */ 
     /*********************************************************/
     public static Request newMyUploadPhotoRequest( Session session,  Bitmap image, String caption, String description, Callback callback )
     {
          Bundle parameters = new Bundle(3);
          parameters.putParcelable( PICTURE_PARAM, image );
          parameters.putString( "caption", caption );
          parameters.putString( "description", description );
     
          return new Request( session, MY_PHOTOS, parameters, HttpMethod.POST, callback );
     }
}
