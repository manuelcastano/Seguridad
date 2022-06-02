
# Informe

## Desarrollo del proyecto 

Este proyecto tiene dos modulos principales: backend y el almacenamiento de datos. Para el backend lo desarrollamos en java y la iteración con el usurario se llevo a cabo por consola. Para la solución de almacenamiento de datos,lo realizamos en un archivo de texto, con estos módulos cumplimos con las necesidades del proyecto 

El backend se encarga del proceso de encriptacion de las contraseñas de lo usuarios usando la librería *pbkdf2*. Las contraseñas son encriptadas antes de ser almacenadas en el archivo de texto, por lo que no queda ningún registro de la contraseña original.Al momento de iniciar sesión, se encripta la contraseña ingresada y se compara con la contraseña encriptada almacenada en el archivo de texto, esto por que podría ser mas demorado desencriptar la contraseña almacenada en el archivo de texto para compararla con la contraseña en texto plano que el usuario ingresa para iniciar sesión.


## Dificultades encontradas:


Las dificultades presentadas a la hora de realizar este proyecto van relacionadas directamente con la manipulación de archivos, ya que desde el inicio se buscó que el programa tuviese la capacidad de realizar tareas como leer, modificar y eliminar , pero al ejecutar todas estas funcionalidades se requerían permisos  de administrador , cosa que entorpeció el proceso. Así mismo a la hora de leer  se presentó una situación adicional que impedía el acceso al mismo archivo si no se cerraba de la forma adecuada después de su lectura. 

## Conclusiones:


- Actualmente resulta ser muy practico implementar seguridad en una aplicación o plataforma web.La documentación que utilizamos para realizar la encriptacion en general son bastantes descriptivos y esta bien desarrollada,en muchas áreas del software hay recursos con muy mala documentación que hacen que su implementación sea más difícil, hacer que la seguridad sea fácil de implementar es necesario para que el software sea bien desarrollado.

- Si se tienen acceso al almacenamiento de los datos, con toda la información encriptada, y al código fuente de la aplicación todos los usuarios quedan expuestos. Nos recordó que la seguridad no es solo un aspecto que se implementa en el código, es además una serie de prácticas que rodean el código, sus desarrolladores y sus usuarios.


## Developed by 🛠️

* **Manuel David Castaño Saldarriaga** [Castaño](https://github.com/manuelcastano)
* **Jaime Andrés Mayor Aldana**  [AndresMayor](https://github.com/AndresMayor)🚀
* **Andres Felipe Cuellar**  [AndresCuellar](https://github.com/andrescuellar123)
