este projeto feito em java em conjunto com Spring-3 e ,infelizmente, ainda está incompleto mas no momento o que voce ira encontrar:

a possibilidade de criar topicos:
para criar um topico voce precisa se registrar no banco utilizando os controllers e recebendo o token deverá ler o token para recuperar o login e passá-lo no DTO-
-junto de outras informações.

a possibilidade de listar e ler topicos:
para listar ou ver os topicos em si não é nescessario estar cadastrado como um usuario, voce pode listá-los e ver aas listas do que será implementado mais a frente

a possibilidade de curtir os topicos e usuarios:
para curitr um topico será nescessario se cadastrar e passar seu login que deverá ser passado pelo front ao receber o token desencriptá-lo com o Bcrypt e assim recuperar o-
-login do usuario

para mais informações poderá acessar a documentação que aparece no final quando o spring boot termina de iniciar
