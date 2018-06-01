# Processo Seletivo Brasilcap - Analista Desenvolvedor JAVA
  
## Cenário
A Brasilcap é uma empresa que auxilia pessoas a guardar dinheiro através de títulos de capitalização.  Ao comprar um título no App você foi premiado instantaneamente e precisa resgatar o seu prêmio em uma agência. Existem agências em diversos bairros e o modelo a seguir demonstra os caminhos possíveis entre os bairros.

Dada uma simplificação do modelo, todas as rotas entre esses bairros são unidirecionais.
Portanto, mesmo que de fato exista uma via bidirecional entre dois bairros, as suas rotas não necessariamente terão a mesma distância de ida e de volta.

## Objetivo

O cliente deve ter disponível as melhores rotas entre alguns bairros e suas devidas distâncias, para que possa optar pelo melhor caminho para resgatar o seu prêmio. Para isso, você foi contratado com o intuito de prover Endpoints REST de algumas funcionalidades comuns. 

## Detalhes Técnicos

Você deverá implementar mais do que um simples algoritmo de solução. Estamos esperando uma aplicação executável com uma estrutura mínima. Você deverá modelar essa estrutura de objetos e utilizar Design Patterns sempre que apropriado e necessário, mas não se esqueça de manter tudo o mais simples possível.

1. A aplicação já contém a estrutura Maven necessária para sua construção.
2. Garanta que sua suíte de testes faça parte da etapa de construção e esteja executando com sucesso.
3. A aplicação pode ser inicializada pelo simples comando Maven: mvn spring-boot:run
4. Você pode definir outra maneira de inicializar sua aplicação conforme se sentir mais confortável, porém além de recomendarmos a utilização do Spring Boot por maior simplicidade, também recordamos que você não deve quebrar essa inicialização já existente. Sua aplicação deve funcionar perfeitamente utilizando o comando de inicialização do Spring Boot.
5. A aplicação deve conter a API stateless conforme especificação e usar um banco de dados local para salvar os dados, quando necessário. 
6. Um banco de dados em memória H2 já está disponível e configurado para utilização na base do projeto disponibilizado. Sinta-se à vontade para alterar essa implementação, mas você deve garantir que sua aplicação continue funcionando independente do ambiente externo.
7. A criação do banco de dados, assim como sua estrutura de tabelas, deverá acontecer de forma automática em tempo de execução (Isso também já está configurado, apenas garanta que sua modelagem seja feita de forma coerente). 
8. Não se esqueça de documentar qualquer informação adicional que você acreditar que seja importante para avaliação do seu projeto.

Dica -> Documentação Recomendada: https://spring.io/guides/gs/rest-service/

## Critério de aceitação

A entrada será dada como um grafo direcionado onde um nó representa um bairro e uma aresta representa uma rota entre dois bairros. 
O peso da aresta representa então, a distância dessa rota. 
Uma dada rota jamais aparecerá mais de uma vez; e para uma dada rota, os bairros de origem e destino sempre serão diferentes.

Uma rota direcionada será dada como um objeto JSON, onde os bairros serão nomeados usando letras do alfabeto [A-Z]. 
Exemplo: uma rota de A para B com distância 5 é representada como:

```javascript
{ 
  "source": "A",
  "target": "B",
  "distance":5
}
```

![Exemplo gráfico do Grafo](Graph.png)

## Funcionalidades Esperadas (Especificação Funcional)

### Salvar Grafo

Esse endpoint deverá receber as arestas de um grafo e salvá-las em um banco de dados para consultas posteriores.

* Endpoint: `http://localhost:8080/graph`
* HTTP Method: POST
* HTTP Success Response Code: CREATED (201)
* Contract:
  * Request payload

```javascript
{
  "data":[
    { 
      "source": "A", "target": "B", "distance":6
    },
    { 
      "source": "A", "target": "E", "distance":4
    },
    { 
      "source": "B", "target": "A", "distance":6
    },
    { 
      "source": "B", "target": "C", "distance":2
    },
    { 
      "source": "B", "target": "D", "distance":4
    },
    { 
      "source": "C", "target": "B", "distance":3
    },
    { 
      "source": "C", "target": "D", "distance":1
    },
    { 
      "source": "C", "target": "E", "distance":7
    },
    { 
      "source": "E",  "target": "B", "distance":5
    },
    { 
      "source": "E", "target": "D", "distance":7
    }
  ]
}
```

  * Response payload

```javascript
{
  "id" : 1,
  "data":[
    { 
      "source": "A", "target": "B", "distance":6
    },
    { 
      "source": "A", "target": "E", "distance":4
    },
    { 
      "source": "B", "target": "A", "distance":6
    },
    { 
      "source": "B", "target": "C", "distance":2
    },
    { 
      "source": "B", "target": "D", "distance":4
    },
    { 
      "source": "C", "target": "B", "distance":3
    },
    { 
      "source": "C", "target": "D", "distance":1
    },
    { 
      "source": "C", "target": "E", "distance":7
    },
    { 
      "source": "E",  "target": "B", "distance":5
    },
    { 
      "source": "E", "target": "D", "distance":7
    }
  ]
}
```

### Recuperar Grafo

Esse endpoint deverá retornar um grafo previamente salvo no banco de dados. Se o grafo não existe, deverá retornar HTTP NOT FOUND.

* Endpoint: `http://localhost:8080/graph/<graphId>`
* HTTP Method: GET
* HTTP Success Response Code: OK (200)
* HTTP Error Response Code: NOT FOUND (404)
* Contract:
  * Request payload: none

  * Response payload

```javascript
{
  "id" : 1,
  "data":[
    { 
      "source": "A", "target": "B", "distance":6
    },
    { 
      "source": "A", "target": "E", "distance":4
    },
    { 
      "source": "B", "target": "A", "distance":6
    },
    { 
      "source": "B", "target": "C", "distance":2
    },
    { 
      "source": "B", "target": "D", "distance":4
    },
    { 
      "source": "C", "target": "B", "distance":3
    },
    { 
      "source": "C", "target": "D", "distance":1
    },
    { 
      "source": "C", "target": "E", "distance":7
    },
    { 
      "source": "E",  "target": "B", "distance":5
    },
    { 
      "source": "E", "target": "D", "distance":7
    }
  ]
}
```

### Encontrar todas rotas disponíveis dado um bairro de origem e outro de destino

Esse endpoint deverá calcular todas as rotas disponíveis de um bairro de origem para outro de destino, dado um número máximo de paradas. 
Se não existirem rotas possíveis, o resultado deverá ser uma lista vazia. Se o parâmetro "maxStops" não for definido, você deverá listar todas as rotas possíveis.

Exemplo: No grafo (AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7), as possíveis rotas de A para C com máximo de 3 paradas seriam: ["ABC", "ADC", "AEBC"]

* Endpoint: `http://localhost:8080/routes/from/<town1>/to/<town2>?maxStops=<maxStops>`
* HTTP Method: POST
* HTTP Response Code: OK (200)
* Contract:
  * Request payload

```javascript
{
  "data":[
    { 
      "source": "A", "target": "B", "distance":5
    },
    { 
      "source": "B", "target": "C", "distance":4
    },
    { 
      "source": "C", "target": "D", "distance":8
    },
    { 
      "source": "D", "target": "C", "distance":8
    },
    { 
      "source": "D", "target": "E", "distance":6
    },
    { 
      "source": "A", "target": "D", "distance":5
    },
    { 
      "source": "C", "target": "E", "distance":2
    },
    { 
      "source": "E", "target": "B", "distance":3
    },
    { 
      "source": "A", "target": "E", "distance":7
    }
  ]
}
```

  * Response payload

```javascript
{
  "routes": [
    {
      "route": "ABC",
      "stops": 2
    },
    {
      "route": "ADC",
      "stops": 2
    },
    {
      "route": "AEBC",
      "stops": 3
    }
  ]
}
```

### Encontrar todas as rotas disponíveis, dado um bairro de origem e outro de destino em um grafo salvo anteriormente

Esse endpoint deverá fazer exatamente o mesmo que o anterior, porém utilizando um grafo salvo anteriormente. Se o grafo não existir, deverá retornar HTTP NOT FOUND.

* Endpoint: `http://localhost:8080/routes/<graphId>/from/<town1>/to/<town2>?maxStops=<maxStops>`
* HTTP Method: POST
* HTTP Success Response Code: OK (200)
* HTTP Error Response Code: NOT FOUND (404)
* Contract:
  * Request payload: none
  * Response payload

```javascript
{
  "routes": [
    {
      "route": "ABC",
      "stops": 2
    },
    {
      "route": "ADC",
      "stops": 2
    },
    {
      "route": "AEBC",
      "stops": 3
    }
  ]
}
```

### Determinar distância de um caminho específico

Esse endpoint deverá retornar a distância total de um caminho entre uma lista direcionada e específica de bairros. Caso a lista de bairros esteja vazia ou seja unitária, o resultado deverá ser zero. Se o dado caminho não existir, então o resultado deverá ser -1.

* Endpoint: `http://localhost:8080/distance`
* HTTP Method: POST
* HTTP Response Code: OK (200)
* Contract:
  * Request payload

```javascript
{
  "path":["A", "B", "C", "D"],
  "data":[
    { 
      "source": "A", "target": "B", "distance":6
    },
    { 
      "source": "A", "target": "E", "distance":4
    },
    { 
      "source": "B", "target": "A", "distance":6
    },
    { 
      "source": "B", "target": "C", "distance":2
    },
    { 
      "source": "B", "target": "D", "distance":4
    },
    { 
      "source": "C", "target": "B", "distance":3
    },
    { 
      "source": "C", "target": "D", "distance":1
    },
    { 
      "source": "C", "target": "E", "distance":7
    },
    { 
      "source": "E",  "target": "B", "distance":5
    },
    { 
      "source": "E", "target": "D", "distance":7
    }
  ]
}
```

  * Response payload

```javascript
{
  "distance" : 9
}
```

### Determinar a distância de um caminho específico em um grafo salvo

Esse endpoint deverá fazer exatamente o mesmo que o anterior, porém utilizando um grafo salvo anteriormente. Se o grafo não existir, deverá retornar HTTP NOT FOUND.

* Endpoint: `http://localhost:8080/distance/<graphId>`
* HTTP Method: POST
* HTTP Success Response Code: OK (200)
* HTTP Error Response Code: NOT FOUND (404)
* Contract:
  * Request payload

```javascript
{
  "path":["A", "B", "C", "D"]
}
```

  * Response payload

```javascript
{
  "distance" : 9
}
```

### Determinar a distância mínima entre dois bairros

Esse endpoint deverá determinar a rota cuja distância seja a mínima possível entre dois bairros. Se os bairros de origem e destino forem iguais, o resultado deverá ser zero. Se não existir rota possível entre os dois bairros, então o resultado deverá ser -1.

* Endpoint: `http://localhost:8080/distance/from/<town1>/to/<town2>`
* HTTP Method: POST
* HTTP Response Code: OK (200)
* Contract:
  * Request payload

```javascript
{
  "data":[
    { 
      "source": "A", "target": "B", "distance":6
    },
    { 
      "source": "A", "target": "E", "distance":4
    },
    { 
      "source": "B", "target": "A", "distance":6
    },
    { 
      "source": "B", "target": "C", "distance":2
    },
    { 
      "source": "B", "target": "D", "distance":4
    },
    { 
      "source": "C", "target": "B", "distance":3
    },
    { 
      "source": "C", "target": "D", "distance":1
    },
    { 
      "source": "C", "target": "E", "distance":7
    },
    { 
      "source": "E",  "target": "B", "distance":5
    },
    { 
      "source": "E", "target": "D", "distance":7
    }
  ]
}
```

  * Response payload

```javascript
{
  "distance" : 8,
  "path" : ["A", "B", "C"]
}
```

### Determinar a distância mínima entre dois bairros em um grafo salvo

Esse endpoint deverá fazer exatamente o mesmo que o anterior, porém utilizando um grafo salvo anteriormente. Se o grafo não existir, deverá retornar HTTP NOT FOUND.

* Endpoint: `http://localhost:8080/distance/<graphId>/from/<town1>/to/<town2>`
* HTTP Method: POST
* HTTP Success Response Code: OK (200)
* HTTP Error Response Code: NOT FOUND (404)
* Contract:
  * Request payload: none

  * Response payload

```javascript
{
  "distance" : 8,
  "path" : ["A", "B", "C"]
}
```

## Dados para Teste

Grafo Entrada:
AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7

Casos de Teste:
1. Distância da Rota ABC: 9
2. Distância da Rota AD: 5
3. Distância da Rota ADC: 13
4. Distância da Rota AEBCD: 22
5. Distância da Rota AED: -1 (Inexistente)
6. Rotas de origem C e destino C com um máximo de 3 paradas: 
	- C (0 paradas)
7. Rotas de origem A e destino C com um máximo de 4 paradas:
	- ABC (2 paradas)
	- ADC (2 paradas)
	- AEBC (3 paradas)
	- ADEBC (4 paradas)
8. Distância mínima de A para C: ABC (distância = 9)
9. Distância mínima de B para B: B (distância = 0)

## Finalização do Desenvolvimento

Utilize o projeto como base para o seu desenvolvimento.

Recomendamos que você utilize seu repositório pessoal para organizar suas atividades de desenvolvimento. Trabalhe com commits organizados e envie pequenas alterações para sua branch sempre que tiver o código e aplicação estáveis para execução.

Assim que finalizado seu projeto, enviar link do seu github com o desafio.

## Avaliação

Você será avaliado nos seguintes aspectos, em ordem de prioridade:
1. Performance e correta execução da especificação funcional;
2. Legibilidade de código e consistência de nomenclaturas;
3. Modelagem e OO (Orientação à Objetos);
4. Testes Unitários;
5. Uso apropriado do Java, assim como as frameworks e suas melhores práticas;
6. Completar todas funcionalidades.

Observe que neste projeto é mais importante que você entregue um código de qualidade do que todas as funcionalidades exigidas. 
Seu código será avaliado independente da entrega total de funcionalidades.
É esperado que você desenvolva sem ajuda ou intervenção direta de terceiros, mas encorajamos que você pesquise por soluções e boas práticas sem nenhum tipo de restrição, apenas lembre-se que, caso seja aprovado para a próxima etapa, serão realizadas perguntas na entrevista, a fim de certificar seu conhecimento total sobre a implementação. 
**Jogue limpo!**
