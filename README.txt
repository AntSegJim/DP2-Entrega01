En este README.txt, vamos a recoger los pasos que hay que seguir para incorporar el protocolo HTTPS a nuestros proyectos.

1. Primero debemos abrir el apartado "Símbolo del sistema" de nuestro ordenador.
2. Para crear el certificado de SSL, ejecutamos el comando: -genkeypair -alias "NOMBRE DEL ARCHIVO" -keyalg RSA -keystore "RUTA DONDE QUIERES GUARDAR EL ARCHIVO\NOMBRE DEL ARCHIVO.cert"
3. Al ejecutar este comando, nos pedirá una serie de datos que deberemos rellenar como la contraseña, nuestro nombre, el nombre de nuestra organización, etc. 
4. Con estos pasos previos ya habremos generado nuestro certificado.
5. Ahora deberemos ir a eclipse, en concreto al fichero server.xml el cual se encuentra dentro de la carpeta Servers
6. En este fichero, deberemos descomentar el siguiente apartado
	<Connector port="8443" protocol="HTTP/1.1" SSLEnabled="true"
               maxThreads="150" scheme="https" secure="true"
               clientAuth="false" sslProtocol="TLS" />
7. Y añadir los siguientes campos:
	keystoreFile="RUTA DONDE QUIERES GUARDAR EL ARCHIVO\NOMBRE DEL ARCHIVO.cert"
	keystorePass="CONTRASEÑA QUE PUSIMOS EN EL PASO 3"
8. Por último, deberemos ir al fichero web.xml de nuestro fichero y añadir las siguientes lineas de código:
	
	<security-constraint>
		<web-resource-collection>
			<web-resource-name>NOMBRE DE NUESTRO PROYECTO</web-resource-name>
			<url-pattern>/*</url-pattern>
		</web-resource-collection>
		<user-data-constraint>
			<transport-guarantee>CONFIDENTIAL</transport-guarantee>
		</user-data-constraint>
	</security-constraint>
