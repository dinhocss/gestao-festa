# Projeto de gestão de uma lista de convidados
Para criarmos um projeto do zero utilizamos o *Spring Initializr*, que é uma ferramenta criada pelos desenvolvedores do Spring, que facilita a criação de um projeto baseado no *Spring Framework*. Antes de falar sobre a inicialização do projeto, precisamos primeiro falar sobre o projeto em si.

Vamos criar um projeto simples, do zero, para aprendermos a utilizar os frameworks Spring Boot, Spring MVC, Spring Data JPA e o Thymeleaf, para vermos como eles funcionam em conjunto.  Nossa aplicação será uma gestão de lista de convidados, onde há um campo referente ao nome e outro referente ao número de convidados que o convidado terá. 

Na aplicação, teremos uma única tela com dois campos de entrada de texto, um para nome do convidado e outro para quantidade de acompanhantes. Teremos também um botão para adicionar e uma tabela que exibirá os convidados cadastrados.

# Criando o projeto pelo Spring Initializr

Para criar o projeto é fornecido um site que realiza algumas configurações iniciais, como dependências, nome e tipo do projeto. Segue abaixo um modelo do projeto preenchido:

![image](https://github.com/user-attachments/assets/98c084e7-f5c9-4c7b-9d90-556dc2b5a6b7)


As dependências a serem instaladas serão as seguinte:
* *Lombok*: Ajuda a reduzir a quantidade de código repetitivo, pois gera automaticamente alguns métodos comuns, como *getters*, *setters*, *equals*, *hashcode*, *toString*, e até mesmo construtores.
* *Thymeleaf*: Utilizado para criação de templates que permite desenvolver páginas HTML de maneira dinâmica em aplicações web.
* *H2 Database*: É um banco de dados relacional leve, rápido e de código aberto, escrito em Java. Ele é amplamente utilizado para testes e desenvolvimento.
* *Spring Data JPA*: Utilizado para simplificar o acesso ao banco de dados e outros armazenamentos de dados no ecossistema Spring
* *Spring Boot DevTools*: Oferece funcionalidades para aumentar a produtividade do desenvolvedor, eliminando a necessidade de certas tarefas manuais, como reiniciar o servidor toda vez que há alteração no código. 
* *Spring Web:* É um módulo projetado para aplicações web. Ela fornece as bibliotecas e ferramentas necessárias para desenvolver uma API RESTful. 

Após a configuração um arquivo .rar será gerado. É necessário descompactar esse arquivo e abrí-lo através da IDE de preferência. No meu caso utilizei Eclipse. 

# Informações iniciais sobre o código

Apos abrirmos o diretório no qual o arquivo gerado pelo Spring Initializr foi descompactado, uma estrutura será criada na barra de explorer.

## A classe GestaoFestaApplication

> Classe responsável por inicializar a aplicação.

Em src/main/java temos o pacote com.algaworks.festa, e dentro dele temos a classe GestaoFestaApplication.java. Segue abaixo o código apresentado por esta classe:

```
package com.algaworks.festa;

import org.springframework.boot.SpringApplication;

@SpringBootApplication
public class GestaoFestaApplication{

	public static void main(String[] args){
		SpringApplication.run(GestaoFestaApplication.class, args);
	}

}
```

A primeira anotação que iremos ver é a *@SpringBootApplication*, que é uma anotação que combina outras três anotações:
* *@Configuration:* Indica que a classe possui configurações de beans para o contexto da aplicação, ou seja, como os beans serão criados e gerenciados dentro do Spring.
* *@EnableAutoConfiguration:* Ativa a configuração automática do Spring Boot, configurando vários aspectos do projeto com base nas dependências adicionadas.
* *@ComponentScan:* Permite que o Spring escaneie o pacote atual e seus subpacotes em busca de componentes como @Controller, @Service e @Repository.


A anotação *@SpringBootApplication* marca a classe como ponto de entrada da aplicação Spring Boot. Quando rodamos a aplicação o Spring Boot:
* Escaneia o projeto
* Configura automaticamente os recursos com base no que foi encontrado (beans, conexões de banco de dados, etc.)

*SpringApplication.run* é o método responsável por dar início a nossa aplicação. GestaoFestaApplication.class é a classe principal da aplicação e deve ser passada como parâmetro, junto com args. 

Ao rodarmos esse código:
* O Spring Boot inicializará a aplicação.
* Será iniciado um servidor embutido (como TomCat) na porta 8080.
* A partir daí podemos adicionar controladores, serviços e configurações com banco de dados.

# Criando o controller

O controlador é responsável por receber a requisição HTTP, realizar algum procedimento, se necessário, e retornar o nome de uma view que será renderizada e mostrada ao usuário. Depois que o controlador processa a requisição, ele indica ao Spring MVC qual página deve ser renderizada. O controlador basicamente decide qual template o usuário vai ver, e isso é feito retornando o nome da view (não o conteúdo dela, apenas o nome).

Segue abaixo um código inicial para o controller:
```
package com.algaworks.festa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ConvidadosController {

	@GetMapping("/convidados")
	public String Listar() {
		return "ListaConvidados";
	}
}
```

O código acima cria um controlador que irá receber uma requisição do tipo *GET*, e retornará o nome de uma view para ser renderizada. Vamos analisar o código por partes. A anotação *@Controller* será responsável por informar ao Spring que a classe ConvidadosController será responsável por gerenciar requisições, ou seja, atuar como um controlador. Ela também marca essa classe como bean, permitindo que o Spring instancie e a mantenha no contexto da aplicação.   

A anotação *@GetMapping("/convidados")* é usada para mapear uma URL de requisição HTTP para um método específico. O método Listar() não possui nenhuma lógica de negócios, ele retorna apenas o nome da view 'ListaConvidados'. Isso significa que o Spring vai procurar um arquivo chamado ListaConvidados.html(ou outro formato de template configurado).

O resultado do código é que quando o usuário acessar a URL `http://localhost:8080/convidados`, o método Listar() será chamado e o Spring MVC irá procurar uma view chamada 'ListaConvidados' para renderizar e enviar de volta ao navegador.

# Criando a página

Toda página que será exibida ao usuário será adicionada em *src/main/resource/templates*. Lembrando que a view precisa ter o mesmo nome do arquivo, no nosso caso será *ListaConvidados.html*. Também adicionamos duas propriedades do Thymeleaf dentro do arquivo *src/main/resource/application.properties*:
* spring.thymeleaf.mode=**html**: Altera par html o modo de template que o Thymeleaf irá trabalhar.
* spring.thymeleaf.cache=false: Para não fazer cache das páginas.

Uma última configuração foi em relação a adição do bootstrap, para facilitar nossos códigos html e css. Para isso, utilizamos a biblioteca webjars. Para podermos utilizar o bootstrap precisamos adicionar as devidas dependências no arquivo *pom.xml*, sendo elas:
```
<dependency>
	<groupId>org.webjars</groupId>
	<artifactId>webjars-locator</artifactId>
</dependency>
<dependency>
	<groupId>org.webjars</groupId>
	<artifactId>bootstrap</artifactId>
	<version>3.3.7</version>
</dependency>
<dependency>
	<groupId>org.webjars</groupId>
	<artifactId>jquery</artifactId>
	<version>2.1.1</version>
</dependency>
```

Após feitas as configurações iniciais podemos começar a criar nosso código html. Segue abaixo como o código ficou:
```
<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org">
<head>
	<meta charset="UTF-8"/>
	<meta name="viewport" content="width=device-width"/>
	
	<title>Lista de Convidados</title>

	<link th:href="@{/webjars/bootstrap/css/bootstrap.min.css}" rel="stylesheet"/>
	<link th:href="@{/webjars/bootstrap/css/bootstrap-theme.min.css}" rel="stylesheet"/>
</head>

<body>
	<h1>Teste</h1>
	<script th:src="@{/webjars/jquery/jquery.min.js}"></script>
	<script th:src="@{/webjars/bootstrap/js/bootstrap.min.js}"></script>
</body>

</html>
```

**OBS:** Dentro do Thymeleaf somos obrigados a fechar todas as tags, até mesmo as de autofechamento.

O código foi estruturado da seguinte maneira:
1. **Declaração do tipo de documento**
	`<!DOCTYPE html>`
	* Indica que o documento é do tipo HTML5.

2. **Declaração de namespaces**
	`<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org">`
	* `xmlns="http://www.w3.org/1999/xhtml"`: Define que este documento segue o padrão XHTML.
	* `xmlns:th="http://www.thymeleaf.org"`: Declara o namespace do Thymeleaf permitindo o uso de atributos específicos como `th:href`,`th:src`, etc.

3. **Cabeçalho(head)**
```
<link th:href="@{/webjars/bootstrap/css/bootstrap.min.css}" rel="stylesheet"/>
<link th:href="@{/webjars/bootstrap/css/bootstrap-theme.min.css}" rel="stylesheet"/>

```

* `th:href`: Thymeleaf substitui @{} pelo caminho correto dos arquivos.
* Os arquivos CSS do Bootstrap são importados:
	* `bootstrap.min.css`: Contém o CSS principal do Bootstrap.
	* `bootstrap-theme.min.css`: Adiciona temas extras do Bootstrap.
	
4. **Inclusão de scripts**
```
<script th:src="@{/webjars/jquery/jquery.min.js}"></script>
<script th:src="@{/webjars/bootstrap/js/bootstrap.min.js}"></script>

```

* O jQuery e o Bootstrap JavaScript são incluídos para funcionalidades dinâmicas:
	* `jquery.min.js`: Biblioteca JavaScript para manipulação de DOM e eventos.
	* `bootstrap.min.js`: Scripts para recursos do Bootstrap, como modais, sliders e colapsos.
* `th:src`: Thymeleaf resolve o caminho para os arquivos no servidor.

# Criando o repositório de convidados

O objetivo aqui é criar uma classe que irá representar os convidados e um repositório para guardar informações sobre esse convidado. Para isso, vamos criar uma classe que será uma entidade de convidado. Dentro dessa classe teremos os atributos nome, quantidade de convidados e ID. Segue abaixo o código referente ao repositório de convidados:

```
package com.algaworks.festa.model;

import java.io.Serializable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Convidado implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private Long id;
	

	private String nome;
	private Integer quantidadeAcompanhantes;

}

```


Vamos analisar o código acima:
* Utilizamos a interface Serializable, que nos permite uma serialização do objeto da classe. Isso faz com que o objeto seja convertido em uma sequência de bytes para armazenamento ou transmissão.
* Os imports `jakarta.persistence.Entity`, `jakarta.persistence.GeneratedValue` e `jakarta.persistence.Id` permitem a utilização de anotações Entity (responsável por mapear a classe para uma tabela), GeneratedValue (faz com que o identificador gere valores automaticamente) e Id (informa que o atributo é uma chave primária no banco de dados).
* Precisamos utilizar a anotação @Entity para informar que a classe em questão será uma tabela em um banco de dados. Precisamos também informar a chave primária, através da anotação @Id. No código, utilizamos @Id e @GeneratedValue, informando que o atributo id além de ser chave primária, também irá gerar valores automáticos.
* O JPA não mapeia atributos que possuam final ou static, portanto, apenas os atributos id, nome e quantidadeAcompanhantes serão mapeados. O atributo serialVersioIUD é utilizado para evitar a quebra de compatibilidade, caso haja mudança na classe, causando conflito com objetos que foram serializados anteriormente.
* As anotações @Getter, @Setter e @NoArgsConstructor são da biblioteca lombok, e são utilizadas para gerar getters e setters automaticamente, e para informar que o construtor não possui argumentos. 

**OBS:** O nome da tabela será igual ao nome da classe, a menos que seja dito explicitamente, através da anotação *@Table(name = '')*. O mesmo pode ser feito com cada coluna, através da anotação *@Column(name='')*.

O repositório será responsável por lidar com a manipulação dos dados do banco de dados. Para isso, é necessário criar uma interface que estenda a classe JpaRepository. Essa classe nos fornece métodos como findAll(), save(), edit(), etc, que lidarão com os dados do banco de dados. O código abaixo representa essa interface:

```
package com.algaworks.festa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.algaworks.festa.model.Convidado;

public interface Convidados extends JpaRepository<Convidado, Long>{

}
```

**OBS:** Note que JpaRepository possui dois parâmetros, Convidado e Long. O primeiro refere-se a entidade sobre o qual estamos trabalhando. E o segundo parâmetro refere-se ao tipo do identificador.

# Enviando um objeto do controller para a view

Anteriormente nós vimos que um controller pode retornar o nome de uma view, que está dentro dos templates e tem o formato html, e essa view será passada para o usuário. Podemos, além de passar uma view, passar também um objeto do controller. No nosso caso, passaremos os dados da nossa entidade para a view. 

A conexão entre a lógica da nossa aplicação e a view é dado através da classe *ModelAndView*. Segue abaixo o nosso controller atualizado:
```
package com.algaworks.festa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import com.algaworks.festa.repository.Convidados;

@Controller
public class ConvidadosController {

@Autowired
private Convidados convidados;

@GetMapping("/convidados")
public ModelAndView Listar() {
	ModelAndView modelAndView = new ModelAndView("ListaConvidados");

	modelAndView.addObject("convidados",convidados.findAll());

	return modelAndView;
	}
}
```

Considerações sobre o código:
* Importamos as bibliotecas `org.springframework.beans.factory.annotation.Autowired` e `org.springframework.web.servlet.ModelAndView`, além das já importadas anteriormente.
* Utilizamos a anotação *@Autowired* para injetar uma instância de Convidados no controller. Fizemos isso para acessarmos os dados presentes na entidade convidados.
* *@GetMapping("/convidados")* é um endpoint que irá mapear o método com o endereço fornecido. Ou seja, quando acessarmos o endpoint, o método Listar() será executado e retornado para o cliente.
* Modificamos o tipo do método para ModelAndView, ao invés de String, pois retornaremos além da nossa view, os dados do model. Para isso, tivemos que instanciar um ModelAndView com o nome da nossa view. 
* Em seguida, adicionamos a nossa variável modelAndView o objeto desejado, que nesse caso foi a tabela presente em Convidados, através do método convidados.findAll(). O retorno é do tipo ModelAndView, o que é esperado. 

# Listando objetos com Thymeleaf

Para exibirmos nossa tabela na view precisamos criar uma tabela através do código html e adicionar cada elemento do nosso banco de dados. Para isso, criamos o seguinte código:

```
<body>
	<div class="panel panel-default" style="margin:10px">
		<div class="panel-heading">
			<h1 class="panel-title">Lista de Convidados</h1>
		</div>

		<div class="panel-body">
			<table class="table">
				<thead>
					<tr>
						<th>Nome</th>
						<th>Acompanhantes</th>
					</tr>
				</thead>
				<tbody>
					<tr th:each="convidado : ${convidados}">
						<td th:text="${convidado.nome}"></td>
						<td th:text="${convidado.quantidadeAcompanhantes}"></td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>

	<script th:src="@{/webjars/jquery/jquery.min.js}"></script>
	<script th:src="@{/webjars/bootstrap/js/bootstrap.min.js}"></script>do
</body>
```

Notas sobre o código acima:
* 
