En este README.txt, vamos a recoger los pasos que hay que seguir para incorporar el protocolo HTTPS a nuestros proyectos.

1. Primero debemos abrir el apartado "S�mbolo del sistema" de nuestro ordenador.
2. Para crear el certificado de SSL, ejecutamos el comando: -genkeypair -alias "NOMBRE DEL ARCHIVO" -keyalg RSA -keystore "RUTA DONDE QUIERES GUARDAR EL ARCHIVO\NOMBRE DEL ARCHIVO".cert
3. Al ejecutar este comando, nos pedir� una serie de datos que deberemos rellenar como la contrase�a, nuestro nombre, el nombre de nuestra organizaci�n, etc. 
4. Con estos pasos previos ya habremos generado nuestro certificado.
5. Ahora deberemos ir a eclipse, en concreto al fichero server.xml el cual se encuentra dentro de la carpeta Servers
6. En este fichero, deberemos descomentar el siguiente apartado
	<Connector port="8443" protocol="HTTP/1.1" SSLEnabled="true"
               maxThreads="150" scheme="https" secure="true"
               clientAuth="false" sslProtocol="TLS" />
7. Y a�adir los siguientes campos:
	keystoreFile="RUTA DONDE HAS GUARDADO EL CERTIFICADO\NOMBRE DEL ARCHIVO".cert
	keystorePass="CONTRASE�A QUE PUSIMOS EN EL PASO 3"
8. Por �ltimo, deberemos ir al fichero web.xml de nuestro fichero y a�adir las siguientes lineas de c�digo:
	
	<security-constraint>
		<web-resource-collection>
			<web-resource-name>NOMBRE DE NUESTRO PROYECTO</web-resource-name>
			<url-pattern>/*</url-pattern>
		</web-resource-collection>
		<user-data-constraint>
			<transport-guarantee>CONFIDENTIAL</transport-guarantee>
		</user-data-constraint>
	</security-constraint>
9. Ahora deberemos ir a nuestro navegador y escribir la siguiente url: https://localhost:8443/"NOMBRE DE NUESTRO PROYECTO"
10. Al pulsar al enter, nos saldra una pantalla intermedia donde nos dir� que nuestra conexi�n no es segura. All� deberemos pulsar el boton "Add Exception..." y aceptar. Con ello ya tendremos acceso a nuestra p�gina.

-------------------------------------------

ENROLMENT

Si el estatus de un enrolment esta a 3, significa que la Brotherhood ha seleccionado otra posici�n para el member que ha enviado la petici�n de enrolment y ahora el miembro ser� quien decida si acepta la nueva posici�n ofrecida por la Brotherhood o la desestima.

------------------------------------------


