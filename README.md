# Manual de usuario

## Descripción del parcial 2 de compiladores

Básicamente el proyecto se basa en la estrucutra de un formato JSON para que sea procesado por un analizador léxico que sería dado por `jsonSim.flex` y un analizador sintáctico que sería dado por `parser.cup`. 

## Funcionamiento

### 1. **Lectura desde archivo**
El programa lee el contenido del archivo `ejemplo_entrada.txt`

### 2. **Método de análisis `leerJSONDelArchivo()`**
   - Método privado que se encarga de leer y validar el archivo
   - Maneja excepciones apropiadamente
   - Proporciona mensajes de error si el archivo no se encuentra

### 3. **Generación automática de archivo `.dot`**
   - Al ejecutar el test, se genera automáticamente el archivo `ast_entrada.dot`
   - Este archivo se guarda en `target/ast/ast_entrada.dot`
   - Contiene la representación gráfica del Árbol Sintáctico Abstracto

## Cómo usar

### Modificar el JSON a analizar:

1. Abre el archivo `ejemplo_entrada.txt` (en la raíz del proyecto)
2. Reemplaza el contenido con el JSON que desees analizar
3. Guarda los cambios

### Ejecutar el test desde la línea de comandos:

```bash
# Opción 1: Ejecutar solo ParserTest
mvn test -Dtest=ParserTest

# Opción 2: Ejecutar todos los tests
mvn test
```

### Resultados:

- Se mostrará en la consola el JSON que se está procesando
- Si el JSON es válido, se mostrará la representación DOT del AST
- Se generará el archivo `target/ast/ast_entrada.dot` con la visualización del árbol

## Estructura del test

```
ParserTest.java
├── parseJSON(String input_json)
│   └── Convierte JSON string a AST usando Lexer y Parser
├── testParser()
│   ├── Lee JSON desde ejemplo_entrada.txt
│   ├── Valida que no esté vacío
│   ├── Parsea el JSON
│   └── Genera visualización Graphviz
└── leerJSONDelArchivo(String nombreArchivo)
    └── Lee el archivo desde dos ubicaciones posibles
```

## Ejemplos de JSON para probar

### Ejemplo 1: JSON sin errores
```json
{
  "squadName": "Super hero squad",
  "homeTown": "Metro City",
  "formed": 2016,
  "secretBase": "Super tower",
  "active": true,
  "members": [
    {
      "name": "Molecule Man",
      "age": 29
    }
  ]
}
```

### Ejemplo 2: JSON con errores
```json
{
  "squadName": "Super hero squad,
  "homeTown": Metro City",
  "formed": 2016,
  "secretBase": "Super tower"
  "active": true,
  "members": [
    {
      "name": "Molecule Man",
      "age": 29,
    }
    {
      "name": "Another Hero"
      "age": 30
    }
  ]
}
```
## Video de demostración del funcionamiento del proyecto: [Video de demostración](https://universidaddavincid-my.sharepoint.com/:v:/g/personal/202302608_estudiante_udv_edu_gt/IQBESvAvM8oeQaULIZP4_9EBASNNLuV9mpl3epOYkomReB4?nav=eyJyZWZlcnJhbEluZm8iOnsicmVmZXJyYWxBcHAiOiJPbmVEcml2ZUZvckJ1c2luZXNzIiwicmVmZXJyYWxBcHBQbGF0Zm9ybSI6IldlYiIsInJlZmVycmFsTW9kZSI6InZpZXciLCJyZWZlcnJhbFZpZXciOiJNeUZpbGVzTGlua0NvcHkifX0&e=rh1HJG)