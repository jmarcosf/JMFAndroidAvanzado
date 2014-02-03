PracticaAndroidAvanzado
-----------------------
**Práctica asignatura Android Avanzado, Master Programación de Apps, U-Tad.**

La práctica consiste en realizar una aplicación que tome una fotografía y la publique en el muro de Facebook con información de localización.

1) *Diseño general de la interfaz de usuario con Actividades y Fragmentos, utilizando correctamente las vistas XML y los menús*.
>Actividades:
>>CMainActivity.java              Activiada principal de la aplicación.
>>CPictureDetailsActivity.java:   Detalles de la fotografía.
>>CLocationsDetailsActivity.java: Detalles del mapa de locaclización donde se tomó la fotografía.
>>CFacebookActivity.java:         Actividad desde donde se envía la fotografía a Facebook.
>>CWidgetProvider.java            Widget Provider.
>>CMyUploadPhotoRequest.java:     Clase para enviar imagen y datos a Facebook en el mismo Request.
>>CShakeDetector.java:            Listener del sensor que detecta la sacudida del dispositivo.

>Menús:
>>Existen botones de menú para tomar una fotografía, para desecharla y para compartirkla en Facebook.

2) *Uso de la cámara para realizar fotografías.*
>Cuando se pulsa sobre el recuadro de la fotografía o en icono del menú se inicia la cámara con la que se puede tomar la fotografía.
>Una vez tomada la fotografía, si se pulsa sobre ella se inicia una actividad con la fotografía en toda la pantalla.

3) *Uso de la localización*.
>La parte inferior de la pantalla principal muestra un mapa con la locacilación del dispositivo. Se actualizada cada 10 segundo y por cada 10 metros.
>Este mapa no responde a gestos pero si se pulsa sobre el se inicia una actividad con el mapa en toda la pantalla que sí.

4) *Uso de geocoding*.
>En la parte superior del mapa indica constantemente información sobre la localización actual.
>Información: Latitud y longitud y la localización exacta del dispositivo.

5) *Integración con Facebook*.
>Una vez tomada la fotografía se habilita un icono que al pulsar sobre el se inicia la activiada que envía la información a Facebook.
>También puede iniciarse al pulsar la opción de menú "Compartir".
>Se envía la fotografía y la información de localización, geocoding, y punto cardinal (grados y texto).
>>NOTA: No he visto en el SDK de Facebook la manera de enviar imagen y texto en el mismo Request
>>      por lo que he añadido una clase derivada que lo haga.

6) *Analíticas*.
>Se envía a Google Analytics un registro cada vez que el usuario entra en una activiadad y cada vez que se pulsa cualquier botón y opción de menú.

7) *Animaciones*.
>La parte superior derecha muestra una brújula con la dirección en grados y punto cardinal al que está apuntado en dispositivo.

9) *Perfecto funcionamiento de la app*.
>Salvo inesperados, la aplicación funciona correctamente.

**Extras**:

1) *Widget*.
>La aplicación contiene un Widget que al pulsar sobre el se inicia la actividad principal.

2) *Punto cardinal en grados*.
>Ademá del punto cardinal en grados también se incluye en texto.

3) *Sacudida del dispositivo*.
>- Al sacudir el dispositivo se descarta la fotografía actual.
>- NOTA: en unos dispositivos es necesario que la sacudida sea más fuerte que en otros. ¿?.
