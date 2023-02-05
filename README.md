# ToDoApp | Aplicativo de lembretes

## Table of contents / Tabela de conteúdo

English
* [General info](#general-info)
* [Technologies](#technologies)
* [Functionalities](#functionalities)
* [Settings](#settings)

Português
* [Informações gerais](#informações-gerais)
* [Tecnologias](#tecnologias)
* [Funcionalidades](#funcionalidades)
* [Configurações](#configurações)

# General info

Tool that allows the creation and storage of reminders in a Firebase database.

Obs: the app is still in development. Some tools may not work properly.

## Technologies

- firebase-auth version: 21.0.7
- Android Studio Chipmunk | 2021.2.1

## Features

- Realtime Database (offline data access is planned);
- Firebase Authentication (only email/password for now, authentication through Google is planned);
- Password / email change;
- Forgot password.

## How to use

- Install the most recent Android Studio version available at: https://developer.android.com/studio; 
- Clone the repository on your machine: git clone https://github.com/dyaghas/ToDoApp.git;
- Execute the app.

## Functionalities

### Register / login

When opening the app for the first time, the user will come across a login screen. If he doesn't have an account, it is possible to access 
the register screen by clicking on 'Register now'. After going through this process, the application functionalities can be accessed.

![image](https://user-images.githubusercontent.com/56042071/191584375-6d51156e-5578-47c9-b20d-074d018c9982.png)
![image](https://user-images.githubusercontent.com/56042071/191584325-f59668e2-9d8a-4117-9efa-e161c0d08c77.png)


### App

The application consists of an activity where it is possible to save small texts accompanied by a date, creating to-dos that will stay
connected to the specific user. Those to-dos can be removed when they are no longer needed.

![image](https://user-images.githubusercontent.com/56042071/191584229-fb48cd6f-45cf-4bb9-9b1f-3275564eb31a.png)

### Settings

The options tab can be accessed through its button in the upper-right screen corner, allowing the user to disconnect from his account
or change his email address.

#### Change Email address

To change the email address, it is necessary to provide the user credentials again. That's a security measure from Firebase Auth.

# Português

# Informações gerais

Ferramenta que permite a criação e o armazenamento de lembretes em um banco de dados Firebase.

Obs: o aplicativo ainda está em desenvolvimento e por isso, algumas funcionalidades podem não ter sido implementadas ou não estarem 
funcionando.

## Tecnologias

- firebase-auth versão: 21.0.7
- Android Studio Chipmunk | 2021.2.1

## Recursos

- Realtime Database (acesso a dados offline planejado);
- Firebase Authentication (apenas através de e-mail / senha. Auntenticação através do Google planejada);
- Alteração de e-mail / senha;
- Esqueci a senha.

## Como usar

- Instale a versão mais recente do Android Studio disponível em: https://developer.android.com/studio;
- Clone o repositório na sua máquina com o comando: git clone https://github.com/dyaghas/ToDoApp.git;
- Execute o projeto no Android Studio.

## Funcionalidades

### Cadastro / login

Ao entrar no aplicativo pela primeira vez, o usuário se deparará com a tela de login. Caso não possua uma conta, basta clicar em "Não possui 
uma conta? Registrar-se" e realizar os procedimentos necessários para acessar as funcionalidades do app.

![image](https://user-images.githubusercontent.com/56042071/191582894-3eabec08-a8c1-44ca-b7e3-9c46886f1c8b.png)
![image](https://user-images.githubusercontent.com/56042071/191582844-22ec2702-fc34-4071-b456-dedf89d51345.png)

### Aplicativo

A aplicação consiste em uma activity onde é possível salvar pequenos textos acompanhados de data, criando lembretes que ficarão conectados à conta
do usuário que está logado no momento. Também há a possibilidade de remover esses lembretes com um toque longo quando não forem mais necessários.

![image](https://user-images.githubusercontent.com/56042071/191583424-e2e97cd3-e65d-42a6-9a2d-dc0900d2844a.png)

### Configurações

A aba de configurações pode ser acessada através do botão no canto direito superior da tela, permitindo que o usuário saia da conta atual ou 
altere seu endereço de email.

#### Alterar endereço de email ou senha

Para alterar o endereço de email ou a senha associada a conta, o usuário precisa inserir suas credenciais novamente. Isso é uma forma do Firebase Auth
garantir que nenhum desses dados seja utilizado de forma incorreta.
