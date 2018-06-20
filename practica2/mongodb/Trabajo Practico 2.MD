# Trabajo Prácito 2 - MongoDB

# Parte 1

## 1. Cuáles de los siguientes conceptos de RDBMS existen en MongoDB? En caso de no existir, ¿hay alguna alternativa? ¿Cuál es?
* Base de Datos --> existe
* Tabla/Relación --> No existe, la alternativa son colecciones, que agrupan documentos
* Fila/Tupla --> No existe, se utilizan documentos, que son objetos JSON
* Columna --> No existe, la alternativa son los atributos/campos de los documentos JSON

## 2. MongoDB tiene soporte para transacciones, pero no es igual que el de los RDBMS. ¿Cuál es el alcance de una transacción en MongoDB?

El alcance es el documento, incluso aunque se modifiquen documentos embebidos

## 3. Para acelerar las consultas, MongoDB tiene soporte para índices. ¿Qué tipos de índices soporta?

* Single field
* Compound index
* Multikey index
* Geospatial index
* Text index
* Hashed index

## 4. ¿Existen claves foráneas en MongoDB?

No existen, se tienen referencias a otros documentos

# Parte 2

## 5. Cree una nueva base de datos llamada airbdb, y una colección llamada apartments. En esa colección inserte un nuevo documento (un departamento) con los siguientes atributos:

```json
    {"name": "Apartment with 2 bedrooms", "capacity": 4}
```

* Para cambiarse a una BD, usar el comando `use <db>`

> Si la BD no existe, la crea

* Para insertar elementos en una colección, usar el comando `db.<coleccion>.insert[One|Many](objeto)`

> Para crear una colección alcanza con mencionarla, si no existe, la crea

```bash
> db.apartments.insertMany( {"name": "Apartment with 2 bedrooms", "capacity": 4} )
```

> Sino puede crearse explícitamente con 
```bash
> db.createCollection('apartments')
```



## Recupere la información del departamento usando el comando db.apartments.find() (puede agregar la función .pretty() al ﬁnal de la expresión para ver los datos indentados). Notará que no se encuentran exactamente los atributos que insertó. ¿Cuál es la diferencia?

```bash
> db.aparments.find().pretty()
```

Devuelve:

```json
{
    "_id" : ObjectId("5b10020d9340a7cbaee96977"),
    "name" : "Apartment with 2 bedrooms",
    "capacity" : 4
}
```

La diferencia es que se agregó un atributo `_id`

## 6. Agregue los siguientes documentos a la colección de departamentos:

```json
{ "name": "New Apartment", "capacity": 3, "services": ["wifi", "ac"] }
{ "name": "Nice apt for 6", "capacity": 6, "services": ["parking"] }
{ "name": "1950s Apartment", "capacity": 3 }
{ "name": "Duplex Floor", "capacity": 4, "services": ["wifi", "breakfast", "laundry"] }
```

> Para agregar los documentos, conviene usar Usar ```db.<coleccion>.insertMany()```

```bash
> db.apartments.insertMany([
    { "name": "New Apartment", "capacity": 3, "services": ["wifi", "ac"] },
    { "name": "Nice apt for 6", "capacity": 6, "services": ["parking"] },
    { "name": "1950s Apartment", "capacity": 3 },
    { "name": "Duplex Floor", "capacity": 4, "services": ["wifi", "breakfast", "laundry"] }
])
```

## Y busque los departamentos:
 - con capacidad para 3 personas.

 ```bash
> db.apartments.find( { capacity: 3  } ).pretty()
 ```

 - con capacidad para 4 personas o más

```bash
> db.apartments.find( { capacity: { $gte: 4 } } ).pretty()
 ```

 - con wiﬁ

```bash
> db.apartments.find({services: 'wifi'}).pretty()
 ```

 - que incluyan la palabra "Apartment" en su nombre

```bash
> db.apartments.find( { name: /Apartment/ } ).pretty()
 ```

 - con la palabra "Apartment" en su nombre y capacidad para más de 3 personas

```bash
> db.apartments.find( { $and: [ { name: /Apartment/ }, { capacity: { $gt: 3 } } ] } ).pretty()
 ```

 - sin servicios (es decir, que el atributo esté ausente). Aún cuando está vacío pero no ausente la siguiente consulta funciona correctamente.

```bash
> db.apartments.find( { services: null } ).pretty()
 ```

 - vuelva a realizar la última consulta pero proyecte sólo el nombre del departamento en los resultados, omitiendo incluso el atributo _id de la proyección.

 ```bash
> db.apartments.find( { services: null }, { _id: 0, name: 1 } ).pretty()
 ```

## En MongoDB hay diferentes maneras de realizar actualizaciones, de acuerdo a las necesidades del esquema ﬂexible de documentos.

* `db.<collection>.updateOne(<filter>, <update>, <options>)`
* `db.<collection>.updateMany(<filter>, <update>, <options>)`
* `db.<collection>.replaceOne(<filter>, <replacement>, <options>)`


 ## 7. Actualice el “Duplex Floor” asignándole capacidad 5.

 ```bash
> db.apartments.updateOne( { name: "Duplex Floor" }, { $set: { capacity: 5 } } )

> db.apartments.findOneAndUpdate(
    { name: "Duplex Floor" },
    { $set: { capacity: 5 } },
    { returnNewDocument: true }
)
 ```

 ## 8. Agregue “laundry” al listado de services del “Nice apt for 6”.

 ```bash
> db.apartments.findOneAndUpdate(
    { name: "Nice apt for 6" },
    { $addToSet: { services: "laundry" } },
    { returnNewDocument: true }
)
 ```

 ## 9. Agregue una persona más de capacidad a todos los departamentos con wiﬁ.

 ```bash
> db.apartments.updateMany(
    { services: "wifi" },
    { $inc: { capacity: 1 } },
    { returnNewDocument: true }
)
 ```
# Parte 3

> Para borar todos los departamentos usar `db.apartments.deleteMany( {} )`

10. ## Busque en la colección de departamentos si existe algún índice deﬁnido.

 ```bash
> db.apartments.getIndexes()
 ```

Devuelve:

```json
[
	{
		"v" : 2,
		"key" : {
			"_id" : 1
		},
		"name" : "_id_",
		"ns" : "airbdb.apartments"
	}
]

```

Por lo tanto, existe un indice para el campo `_id`. Este indice es creado automaticamente por mongo, y sirve para asegurarse que no se puedan crear dos objetos con el mismo `_id`

## 11. Cree un índice para el campo name. Busque los departamentos que tengan en su nombre el string "11" y utilice el método explain("executionStats") al ﬁnal de la consulta, para comparar **la cantidad de documentos examinados** y **el tiempo en milisegundos** de la consulta con y sin índice.

* Sin indice

    ```bash
    > db.apartments.find( { name: /11/ } ).explain("executionStats")
    ```
    Devuelve:

    ```json
    {
        "executionStats" : {
            "executionSuccess" : true,
            "nReturned" : 2291,
            "executionTimeMillis" : 19,
            "totalKeysExamined" : 0,
            "totalDocsExamined" : 50000,
        }
    }
    ```

    * **Cantidad de documentos examinados**: `50000`
    * **Tiempo en milisegundos**: `19ms`

* Con indice:
    * Crear el indice para el campo nombre:

    ```bash
    > db.apartments.createIndex( { name: 'text' } )
    > db.apartments.find( { name: /11/ } ).explain("executionStats")
    ```
    Devuelve:

    ```json
    {
        "executionStats" : {
            "executionSuccess" : true,
            "nReturned" : 2291,
            "executionTimeMillis" : 77,
            "totalKeysExamined" : 0,
            "totalDocsExamined" : 50000,
        }
    }
    ```

    * **Cantidad de documentos examinados**: `50000`
    * **Tiempo en milisegundos**: `77ms`
    > Al volver a ejecutar la consulta baja a `~20ms`

    * Con un indice "comun", los resultados son diferentes:

    ```bash
    > db.apartments.createIndex( { name: 1 } )
    > db.apartments.find( { name: /11/ } ).explain("executionStats")
    ```
    Devuelve:

    ```json
    {
        "executionTimeMillis" : 30,
        "totalKeysExamined" : 50000,
        "totalDocsExamined" : 2291,
    }
    ```

    * **Cantidad de documentos examinados**: `2291`
    * **Tiempo en milisegundos**: `~30ms`

## 12. Busque los departamentos dentro de la ciudad de Londres. Para esto, puede obtener el polígono del archivo provisto greaterlondon.geojson y deﬁnir una variable en la terminal para facilitar la consulta. Cree un índice geoespacial de tipo 2dsphere para el campo location de la colección apartments y, de la misma forma que en el punto 11, compare la performance de la consulta con y sin dicho índice.

> Crear un archivo `geometry.js`, en el cual se asigne a una variable `geometry` el contenido del archivo `greaterlondon.geojson`

* Sin indice

    ```bash
    > load('geonetry.js')
    > db.apartments.find( { location: { $geoWithin: { $geometry: geometry } } } ).explain("executionStats")
    ```
    Devuelve:

    ```json
    {
        "executionTimeMillis" : 130,
        "totalKeysExamined" : 0,
        "totalDocsExamined" : 50000,
    }
    ```

    * **Cantidad de documentos examinados**: `50000`
    * **Tiempo en milisegundos**: `130ms`

* Con indice:
    * Crear el indice para el campo location:

    ```bash
    > db.apartments.createIndex( { location: '2dsphere' } )
    > db.apartments.find( { location: { $geoWithin: { $geometry: geometry } } } ).explain("executionStats")
    ```
    Devuelve:

    ```json
    {
        "executionTimeMillis" : 97,
        "totalKeysExamined" : 18317,
        "totalDocsExamined" : 18296,
    }
    ```

    * **Cantidad de documentos examinados**: `18296`
    * **Tiempo en milisegundos**: `97ms`

# Parte 4

## 13. Obtenga 5 departamentos aleatorios de la colección.

```bash
> db.apartments.aggregate( [ { $sample: { size: 5 } } ] )
```


## 14. Usando el framework de agregación, obtenga los departamentos que estén a 15km (o menos) del centro de la ciudad de Londres `([-0.127718, 51.507451])` y guárdelos en una nueva colección

> Esta seria la consulta que devuelve lo pedido

```bash
> db.apartments.find(
    { location: {
        $near: {
                $geometry: { type: "Point",  coordinates: [ -0.127718, 51.507451 ] },
                $maxDistance: 15000
            }
        }
    }
)
```

> Usando el framework de agregación; tambien guarda la salida en una nueva colecccion `apartmentsNearLondon`

```bash
> db.apartments.aggregate([
    {
        $geoNear: {
            near: { type: "Point", coordinates: [ -0.127718, 51.507451 ] },
            distanceField: "distance",
            maxDistance: 15000,
            spherical: true
        }
    },
    { $out : "apartmentsNearLondon" }
])
```

## 15. Para los departamentos hallados en el punto anterior, obtener una colección con cada departamento agregando un atributo reservas que contenga un array con todas sus reservas. Note que sólo es posible ligarlas por el nombre del departamento.

```bash
> db.apartmentsNearLondon.aggregate([
    {
        $lookup: {
            from: "reservations",
            localField: "name",
            foreignField: "apartmentName",
            as: "reservations"
        }
    }
])
```

## 16. Usando la colección del punto anterior, obtenga el promedio de precio pagado por reserva (precio completo, no dividir por la cantidad de noches) de cada departamento.

```bash
> db.apartmentsNearLondon.aggregate([
    {
        $lookup: {
            from: "reservations",
            localField: "name",
            foreignField: "apartmentName",
            as: "reservations"
        }
    },
    {
        $match: { "reservations": { $ne: [] } }
    },
    {
        $project : {
            _id: 0,
            name: 1,
            from: 1,
            to: 1,
            averagePrice: { $avg: "$reservations.amount" }
        }
    }
]).pretty()
```
