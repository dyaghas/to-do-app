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

## General info

Android application that allows the creation of a to-do list.

Obs: the app is still in development, because of that, it is possible that some tools where not implemented or are not working properly.

## Technologies

- firebase-database
- firebase-analytics
- firebase-auth versão: 21.0.7
- Android Studio Chipmunk | 2021.2.1
  
## Functionalities

### Register / login

When opening the app for the first time, the user will come across a login screen. If he doesn't have an account, it is possible to access 
the register screen by clicking on 'Register now'. After going through this process, the application functionalities can be accessed.


![image](https://user-images.githubusercontent.com/56042071/183947611-13de8b19-6184-47e2-9620-c8ba8e580519.png)
![image](https://user-images.githubusercontent.com/56042071/183948277-248f3a0e-4790-45af-9286-9441d181eab0.png)

### App

The application consists of an activity where it is possible to save small texts accompanied by a date, creating to-dos that will stay
connected to the specific user. Those to-dos can be removed when they are no longer needed.

![image](https://user-images.githubusercontent.com/56042071/183957161-ffda7b59-d9f3-4bc7-8e62-894c20e07c78.png)
![image](https://user-images.githubusercontent.com/56042071/183956834-bc3adf87-6a4b-4cfd-89c3-761e93f5ce6d.png)

### Settings

The options tab can be accessed through its button in the upper-right screen corner, allowing the user to disconnect from his account
or change his email address.

![image](https://user-images.githubusercontent.com/56042071/184500482-a2e2a3e9-1ee8-426a-a76e-fa257a618556.png)
![image](https://user-images.githubusercontent.com/56042071/184655973-c4b618f1-dcca-4b94-a904-e406c5540967.png)

#### Change Email address

To change the email address, it is necessary to provide the user credentials again. That's a security measure from Firebase Auth.

![image](https://user-images.githubusercontent.com/56042071/184975145-a63a04a4-b384-4f27-9074-7c8e84709087.png)
![image](https://user-images.githubusercontent.com/56042071/184973803-c5544288-3ca6-4be6-9676-a79da3c5e741.png)

# Português

## Informações gerais

Aplicativo android que permite a criação de lembretes.

Obs: o aplicativo ainda está em desenvolvimento e por isso, algumas funcionalidades podem não ter sido implementadas ou não estarem 
funcionando.

## Tecnologias

- firebase-database
- firebase-analytics
- firebase-auth version: 21.0.7
- Android Studio Chipmunk | 2021.2.1

## Funcionalidades

### Cadastro / login

Ao entrar no aplicativo pela primeira vez, o usuário se deparará com a tela de login. Caso não possua uma conta, basta clicar em "Não possui 
uma conta? Registrar-se" e realizar os procedimentos necessários para acessar as funcionalidades do app.

![image](https://user-images.githubusercontent.com/56042071/184971921-8d9662bb-a0ff-4387-b468-d2f1db5fa546.png)
![image](https://user-images.githubusercontent.com/56042071/184971718-450c32cf-0f44-45e9-92dc-f57f3443f846.png)

### Aplicativo

A aplicação consiste em uma activity onde é possível salvar pequenos textos acompanhados de data, criando lembretes que ficarão conectados à conta
do usuário que está logado no momento. Também há a possibilidade de remover esses lembretes com um toque longo quando não forem mais necessários.

![image](https://user-images.githubusercontent.com/56042071/184973233-19171021-bc1c-493d-90ea-2c28409a7cd4.png)

### Configurações

A aba de configurações pode ser acessada através do botão no canto direito superior da tela, permitindo que o usuário saia da conta atual ou 
altere seu endereço de email.

![image](https://user-images.githubusercontent.com/56042071/184973311-de000acf-adc5-4fd5-8c0c-fd31741ba11c.png)
![image](https://user-images.githubusercontent.com/56042071/184973360-cd57b929-3602-454c-a026-2cb75421c5a7.png)

#### Alterar endereço de email ou senha

Para alterar o endereço de email ou a senha associada a conta, o usuário precisa inserir suas credenciais novamente. Isso é uma forma do Firebase Auth
garantir que nenhum desses dados seja utilizado de forma incorreta.

![image](https://user-images.githubusercontent.com/56042071/184975938-02a9831a-b389-4ce3-ae42-d4e0dc0f05b4.png)
![image](https://user-images.githubusercontent.com/56042071/184973638-be130b9d-355f-4986-be88-3b10712d340a.png)
