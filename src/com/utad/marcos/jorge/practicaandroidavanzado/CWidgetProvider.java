/**************************************************************/
/*                                                            */
/* CWidgetProvider.java                                       */
/* (c)2014 jmarcosf                                           */ 
/*                                                            */ 
/* Description: CWidgetProvider Class                         */ 
/*              Práctica asignatura Android Avanzado          */ 
/*              U-Tad - Master Apps                           */ 
/*              www.u-tad.com                                 */ 
/*                                                            */ 
/*        Date: January 2014                                  */ 
/*                                                            */ 
/**************************************************************/
package com.utad.marcos.jorge.practicaandroidavanzado;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

/**************************************************************/
/*                                                            */ 
/*                                                            */ 
/*                                                            */ 
/* CWidgetProvider Class                                      */ 
/*                                                            */ 
/*                                                            */ 
/*                                                            */ 
/**************************************************************/
public class CWidgetProvider extends AppWidgetProvider
{
     
     /*********************************************************/
     /*                                                       */ 
     /*                                                       */ 
     /* AppWidgetProvider Override Methods                    */ 
     /*                                                       */ 
     /*                                                       */ 
     /*********************************************************/
     /*                                                       */ 
     /* CWidgetProvider.onUpdate()                            */ 
     /*                                                       */ 
     /*********************************************************/
     @Override
     public void onUpdate( Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds )
     {
          Log.i( CWidgetProvider.class.getSimpleName(), "OnUpdate()" );
          RemoteViews remoteViews = new RemoteViews( context.getPackageName(), R.layout.layout_widget_short );

          Intent intent = new Intent( context, CMainActivity.class );
          PendingIntent pendingIntent = PendingIntent.getActivity( context, 0, intent, 0 );
          remoteViews.setOnClickPendingIntent( R.id.IDR_LAY_WIDGET, pendingIntent );
          appWidgetManager.updateAppWidget( appWidgetIds[ 0 ], remoteViews );
     }
}
