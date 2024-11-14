# EXAMEN ACCESO A DATOS TEMA 2
El programa realiza una conversion de formato xlsx a SQL, lo hace gracias a la utilización de Apache POI.

En mi caso el programa no consigue realizar la conversion debido a una erronea conversión del tipo de dato Date, lo cual me impide la ejecución.

## ANTES DE NADA
Primero debemos crear nuestro docker-compose.yml junto a su .env para cargar el gestor de bases de datos.

```yml
version: '3.1'

services:

  adminer:
    image: adminer
    restart: "no"
    ports:
      - ${ADMINER_PORT}:8080

  db-reservas:
    image: mysql:latest
    restart: "no"
    environment:
      MYSQL_ROOT_PASSWORD: ${MYSQL_ROOT_PASSWORD}
    ports:
      - ${MYSQL_PORT}:3306
    #volumes:
    #  - ./scripts:/docker-entrypoint-initdb.d
```

```bash
MYSQL_ROOT_PASSWORD=9127836901
MYSQL_USERNAME=root
MYSQL_PORT=33310
MYSQL_HOST=localhost 
MYSQL_DATABASE=reservas
ADMINER_PORT=8888
```

También he tenido que iniciar mi repositorio tanto en local como de manera pública, por lo que he añadido un fichero .gitignore y le he metido los archivos basura y los sensibles, en nuestro caso el .env


## EL PROGRAMA

Primero he decidido crear un archivo con FileWriter en el cual se introducirá el codigo SQL, creando en este el codigo SQL de creación de tablas.

```java
FileWriter fw = new FileWriter("C:\\Users\\aula18\\Documents\\MANANA\\ACCESO A DATOS\\tema02\\EXAMEN\\examen\\stack\\scripts\\initdb.sql");

        fw.write("CREATE DATABASAE IF NOT EXISTS `reservas`;\n");
        fw.write("USE `reservas`;\n");

        fw.write("CREATE TABLE Usuarios (\n");
        fw.write("    id INT PRIMARY KEY,\n");
        fw.write("    username VARCHAR(50),\n");
        fw.write("    password VARCHAR(65),\n");
        fw.write("    email VARCHAR(80)\n");
        fw.write(");\n\n");

        for (Usuario usuario : usuarios) {
            fw.write(String.format("INSERT INTO Usuarios (id, username, password, email) VALUES (%d, '%s', '%s', %d);\n",
                    usuario.getId(), usuario.getUsername(), usuario.getPassword(), usuario.getEmail()));
        }
        
        fw.write("\nCREATE TABLE Instalaciones (\n");
        fw.write("  id INT PRIMARY KEY,\n");
        fw.write("  nombre VARCHAR(50)\n");
        fw.write(");\n");

        for (Instalacion instalacion : instalaciones) {
            fw.write(String.format("INSERT INTO Instalaciones (id, nombre) VALUES (%d, '%s');\n",
                    instalacion.getId(), instalacion.getNombre()));
        }

        fw.write("\nCREATE TABLE Horarios (\n");
        fw.write("  id INT PRIMARY KEY,\n");
        fw.write("  instalacion INT,\n");
        fw.write("  inicio DATE,\n");
        fw.write("  fin DATE\n");
        fw.write(");\n");

        for (Horario horario : horarios){
            fw.write(String.format("INSERT INTO Horarios (id, instalacion, inicio, fin) VALUES (%d, '%s', '%s', '%s');\n",
                    horario.getId(), horario.getInstalacion(), horario.getInicio(), horario.getFin()));
        }

        fw.write("\nCREATE TABLE Reservas (\n");
        fw.write("  id INT PRIMARY KEY,\n");
        fw.write("  usuario INT,\n");
        fw.write("  horario INT,\n");
        fw.write("  fecha DATE\n");
        fw.write(");\n");

        for (Reserva reserva : reservas){
            fw.write(String.format("INSERT INTO Reservas (id, usuario, horario, fecha) VALUES (%d, %d, %d,'%s');\n",
                reserva.getId(), reserva.getUsuario(), reserva.getHorario(), reserva.getFecha()));
        }
    }
```


También he pasado la información del xlsx a un List en Java para poder tratarlo, aquí es donde he utilizado las dependencias de Apache POI.

Aquí dejo el ejemplo de uno de las hojas xlsx pasada a List:

```java
public static List<Instalacion> leerTablaInstalaciones(XSSFSheet instalacionesSheet){
        List<Instalacion> instalaciones = new ArrayList<>();
        for (Iterator it = instalacionesSheet.iterator();
         it.hasNext();
         ) {
            Row row = (Row) it.next();
            if (row.getRowNum() == 0) continue;  
            Cell cellId = row.getCell(0);
            Cell cellNombre = row.getCell(1);
            if (cellId != null && cellNombre != null) {
                int id = (int) cellId.getNumericCellValue();
                String nombre = cellNombre.getStringCellValue();
                instalaciones.add(new Instalacion(id, nombre));
            }
        }
        return instalaciones;
    }
```

Luego en el main habría que crea la lista a partir lo que devuelve este método

```java
 List<Usuario> usuarios = leerTablaUsuarios(workbook.getSheet("Usuario"));
```