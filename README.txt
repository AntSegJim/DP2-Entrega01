En este README.txt, vamos a recoger los pasos que hay que seguir para incorporar el protocolo HTTPS a nuestros proyectos.

1. Primero debemos abrir el apartado "S�mbolo del sistema" de nuestro ordenador.
2. Para crear el certificado de SSL, ejecutamos el comando: -genkeypair -alias "NOMBRE DEL ARCHIVO" -keyalg RSA -keystore "RUTA DONDE QUIERES GUARDAR EL ARCHIVO\NOMBRE DEL ARCHIVO.cert"
3. Al ejecutar este comando, nos pedir� una serie de datos que deberemos rellenar como la contrase�a, nuestro nombre, el nombre de nuestra organizaci�n, etc. 
4. Con estos pasos previos ya habremos generado nuestro certificado.
5. Ahora deberemos ir a eclipse, en concreto al fichero server.xml el cual se encuentra dentro de la carpeta Servers
6. En este fichero, deberemos descomentar el siguiente apartado
	<Connector port="8443" protocol="HTTP/1.1" SSLEnabled="true"
               maxThreads="150" scheme="https" secure="true"
               clientAuth="false" sslProtocol="TLS" />
7. Y a�adir los siguientes campos:
	keystoreFile="RUTA DONDE QUIERES GUARDAR EL ARCHIVO\NOMBRE DEL ARCHIVO.cert"
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
