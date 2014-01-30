/**************************************************************/
/*                                                            */ 
/* CPictureDetailsActivity.java                               */ 
/* (c)2014 jmarcosf                                           */ 
/*                                                            */ 
/* Description: CPictureDetailsActivity Class                 */ 
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
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

/**************************************************************/
/*                                                            */ 
/*                                                            */ 
/*                                                            */ 
/* CPictureDetailsActivity Class                              */ 
/*                                                            */ 
/*                                                            */ 
/*                                                            */ 
/**************************************************************/
public class CPictureDetailsActivity extends Activity
{
public static final String IDS_PICTURE_PARAM  = "PictureParam";

private ImageView   m_Image   = null;
private Bitmap      m_Bitmap  = null;

     /*********************************************************/
     /*                                                       */ 
     /*                                                       */ 
     /* Activity Override Methods                             */ 
     /*                                                       */ 
     /*                                                       */ 
     /*********************************************************/
     /*                                                       */ 
     /* CPictureDetailsActivity.onCreate()                    */ 
     /*                                                       */ 
     /*********************************************************/
     @Override
     protected void onCreate( Bundle savedInstanceState )
     {
          super.onCreate( savedInstanceState );
          setContentView( R.layout.layout_activity_picture_details );
          
          Bundle Params = getIntent().getExtras();
          byte[] byteArray = Params.getByteArray( IDS_PICTURE_PARAM );

          m_Bitmap = BitmapFactory.decodeByteArray( byteArray, 0, byteArray.length );
          m_Image = (ImageView)findViewById( R.id.IDC_IMG_PREVIEW );
          m_Image.setImageBitmap( m_Bitmap );
     }
}
