# REGEDAT

_REGEDAT_ es una aplicación desarrollada en **Java** para gestionar las calificaciones
de tus materias. Puedes iniciar sesión e inscribir las materias de tu universidad,
donde pordrás agregar tus calificaciones con el puntaje obtenido y su respectivo
porcentaje de forma que logres conocer fácilmente el promedio final que obtuviste
en tu curso.

> [!IMPORTANT]
> Asegúrate de tener el _Java Development Kit_ instalado **(versión 17 or superior):**
>- [Descargar JDK 17+](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
>
>  `Comprueba que tu sistema tiene la variable de entorno JAVA_HOME establecida en la ubicación de tu JDK.`
> 
> Si deseas trabajar con el código fuente, usa un editor compatible con Java como:
> - [IntelliJ IDEA](https://www.jetbrains.com/idea/download/)
> - [Eclipse](https://www.eclipse.org/downloads/)
>


-------------


### Inicio de programa

Para usar el programa, debes dirigirte a la carpeta `\run` y abrir el archivo `regedat.jar`. Al iniciar correctamente el sistema, aparecerá una ventana con el logo de la
universidad, un campo para ingresar el `código` del estudiante, otro para ingresar su
`contraseña` y al lado los botones `Entrar` y `Rest.`, los cuales sirven para iniciar
sesión con los datos ingresados e iniciar el proceso de recuperación de contraseña
respectivamente.


### Recuperar contraseña

Si el estudiante se encuentra registrado en la base de datos de la universidad y ha
olvidado su contraseña, puede pulsar el botón `Rest.` donde aparecerá un pequeño
cuadro de dialogo en el que podrá ingresar el código de su cuenta y al hacerlo, un
código temporal será enviado a su correo electrónico mientras al mismo tiempo el
sistema pasa a la ventana de recuperación donde aparecerán tres campos: uno para
ingresar el `código OTP` enviado al correo **(el cual tendrá una caducidad de 10 
minutos)** y otros dos para ingresar la `nueva contraseña` y `confirmarla`.

> [!CAUTION]
> - La contraseña debe contener un **número [0-9].**
> - La contraseña debe contener una **letra minúscula [a-z].**
> - La contraseña debe contener una **letra mayúscula [A-Z].**
> - La contraseña debe contener un **símbolo**.
> - La contraseña no debe superar los **64 carácteres**.

Si todo fue realizado correctamente, se le avisará al estudiante que su contraseña
ha sido actualizada y podrá ingresar con su nueva clave, en caso de que el código OTP
haya expirado, tendrá que volver a generar un nuevo código volviendo al menú principal
e ingresando nuevamente su código de estudiante.


### Inscribir una materia

Tras iniciar sesión en el sistema, una ventana aparecerá con un cuadro donde serán
listadas las materias con su respectiva información, al lado izquierdo los botones
`+` para inscribir una nueva materia y `Cerrar` para cerrar la sesión; a la derecha
el botón `Listado` donde el usuario podrá ver las materias disponibles en la universidad
en base a la carrera en la que son cursadas.

Al pulsar el botón `+`, aparecerá un cuadro de diálogo con un campo donde se debe ingresar
el código de la materia a inscribir y al pulsar el botón `Vale` ahora la materia será
mostrada en el cuadro de materias inscritas junto con un botón `+` para ingresar al menú
de notas y otro `x` para eliminar la inscripción a la materia. Si la materia ingresada ya
se encuentra inscrita o no existe, el sistema arrojará un error pidiéndote que ingreses
nuevamente el código por uno válido.


### Crear una calificación

Cuando pulsas el botón `+` de la materia, aparecerá el menú de las calificaciones que
se ve muy similar al anterior, pero con dos nuevos botones: `Total` para calificar el
total que suman las calificaciones y `Volver` para regresar al menú de las materias.
También, en la parte superior izquierda de la pantalla aparecerá un número que indica
cuál es el promedio obtenido hasta el momento en dicha materia y será actualizado a
medida que se calcule el `Total` con las notas que vayan siendo agregadas, eliminadas
o modificadas.

La forma de operar será similar a la del anterior menú, pero con un pequeño cambio:
al pulsar el botón `+` del menú de notas aparecerá un cuadro de diálogo similar al visto
en los anteriores menús, en el cual el estudiante puede ingresar el nombre de la
calificación a agregar **(no mayor a 30 carácteres)** o en su defecto, dejarlo vacío. Al
pulsar el botón `Vale`, una nueva calificacion será agregada con dos campos de texto para
el `puntaje` y `porcentaje` respectivamente, además del botón `x` para eliminarla.


### Calcular el promedio total

Después de terminar de agregar las notas de tu curso, puedes pulsar el botón `Total`
mencionado anteriormente y así, aparecerá un cuadro de texto que te avisará si has
ganado la materia, si la has perdido o cuánto se necesita obtener en el porcentaje restante para poder aprobarla.


> [!CAUTION] En base a este cálculo, se indicará el nivel de riesgo de la materia el cual dependerá de los siguiente criterios:
> 
> - **Sin riesgo:** se debe obtener una calificación `menor o igual` a la mínima aprobatoria en el resto de la materia para poder aprobarla.
> - **Riesgo medio:** se debe obtener una calificación `mayor` a la mínima aprobatoria en el resto de la materia para poder aprobarla.
> - **Riesgo medio:** se debe obtener una calificación casi perfecta, es decir `mayor
o igual` al 85% de la nota máxima en el resto de la materia para poder aprobarla.
> - **Materia pérdida:** se debe obtener una calificación `mayor` a la máxima admitida por la universidad en el resto de la materia para poder aprobarla.
> - **Materia ganada:** se ha obtenido una calificación `mayor o igual` a la mínima aprobatoria.

Estos avisos serán enviados al correo electrónico del estudiante, indicando el nombre
de la materia y cuánto debe sacar en el porcentaje restante para poder aprobarla en caso
de que todavía tenga posibilidad de aprobarla; en caso de que ya se haya aprobado o 
reprobado se enviará un mensaje específico para cada caso.

También, al regresar al menú de las materias se podrá ver que la materia estará coloreada
en base al nivel de riesgo que esta tenga. Los respectivos colores serán:

- **Gris:** sin riesgo.
- **Amarillo:** riesgo medio.
- **Naranja:** riesgo alto.
- **Rojo:** materia pérdida.
- **Verde:** materia aprobada.