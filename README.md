# Spring Boot Email
Spring Boot Email es una pequeña app desarrollada con Spring Boot para recibir peticiones Http Post y reenviar los datos del formulario de mi web personal https://rubennicolasdiaz.github.io/contacto.html a mi cuenta de correo de Yahoo. 

### Funcionamiento
En primer lugar, definimos una clase EmailDTO sencilla con cuatro atributos de tipo String: name, email, subject y message. Una vez hecho esto, se crean las clases para la configuración del reenvío de email (puertos, servidor SMTP, nombre del servidor de correo, etc) y de la configuración de CORS de los navegadores. 

En la configuración de email hay que indicar una variable para nuestra cuenta de correo y otra para el password. Por motivos de seguridad, no se recomienda ponerla directamente hardcodeada, ni tampoco en un fichero dentro del proyecto, sino que cuando se despliegue la app en la nube se le dan los valores a dichas variables. 

Después creamos una plantilla en un fichero Html para que los datos se muestren en nuestra bandeja de correo con el formato deseado. 

El siguiente paso es establecer un servicio con los métodos que hemos establecido en las clases de configuración y en la plantilla Html. 

Por último, se establecen los controladores que recibirán las peticiones Http: El prinicipal recibirá peticiones tipo post al endpoint que definimos en la Url principal: /send-email. En el cuerpo o Body se tiene que incluir como objeto Json los datos del formulario de nuestra web. Ejemplo: 
{
    "name":"nombre",
    "email":"email@mail.com",
    "subject":"ASUNTO", 
    "message": "Mensaje que queremos enviar al correo desde el formulario."
}

Adicionalmente hemos establecido un segundo controlador rest en otra clase para peticiones Get en el endpoint: /health que devuelve un simple "OK". 

Otra configuración muy importante es CORS, relacionada con los navegadores web. Aquí podemos controlar exactamente desde dónde queremos que se llame a un endpoint de nuestra API, en nuestro caso sólo desde el dominio de nuestra web. 

### Dockerización y Despliegue de la app
Una vez establecido el Dockerfile con una imagen ligera (JDK 21) y con la copia de nuestra app a dicha imagen, procedimos a desplegar la app en un servicio online llamado Render. En nuestro caso, hemos aprovechado un plan free para el hacer el despliegue desde este mismo repositorio de Github.

Con el plan free no podemos evitar que la app entre en hibernación a no ser que reciba peticiones http externas cada pocos minutos (unos 15 como máximo), es por ello que se agregó el el segundo controlador. Desde este mismo repositorio de Github, con Actions establecemos un trigger que cada 5 minutos realiza una petición get hacia el segundo controlador con la Url de la app desplegada en Render. De esta forma, evitamos que hiberne y que tarde mucho en volver a levantar para la siguiente petición post.

Una vez tenemos la app desplegada en la nube, copiamos la url y le agregamos el endpoint para el envío de peticiones post: https://spring-boot-email.onrender.com/send-email . La url completa se la pasamos al fichero de configuración de JS de nuestra web para que todas las peticiones del formulario de contacto pasen por el servicio desplegado y éste las redirija a nuestro buzón de email Yahoo.

Para mayor información, se puede revisar el código fuente en este mismo repositorio o visitar el vídeo explicativo del funcionamiento de la app: https://youtu.be/sfBY5qnMnDs