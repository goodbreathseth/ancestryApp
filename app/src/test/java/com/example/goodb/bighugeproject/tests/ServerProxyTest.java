package com.example.goodb.bighugeproject.tests;

import com.example.goodb.bighugeproject.serverProxy.ServerProxy;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import helper.HttpHelper;
import requests.LoginRequest;
import requests.RegisterRequest;
import results.LoginResult;
import results.RegisterResult;

import static org.junit.Assert.*;

public class ServerProxyTest {

    @Before
    public void setUp() throws Exception {
        HttpHelper helper = new HttpHelper();
        RegisterResult registerResult;
        LoginResult loginResult;

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void register() {
        ServerProxy serverProxy = new ServerProxy();
        RegisterRequest registerRequest = new RegisterRequest("Sarah", "hinton", "seth@gmail.com",
                "Seth", "Hinton", "m");
        assertEquals("Sarah",
                serverProxy.register("192.168.1.229", "8080", registerRequest).getUserName());
    }

    @Test
    public void invalidRegister() {
        ServerProxy serverProxy = new ServerProxy();
        RegisterRequest registerRequest = new RegisterRequest();
        assertEquals("Not all fields have been filled",
                serverProxy.register("192.168.1.229", "8080", registerRequest).getMessage());
    }

    @Test
    public void login() {

        ServerProxy serverProxy = new ServerProxy();
        LoginRequest loginRequest = new LoginRequest("sheila", "parker");
        assertEquals("sheila", serverProxy.login("192.168.1.229", "8080", loginRequest).getUserName());
    }

    @Test
    public void invalidLogin() {
        ServerProxy serverProxy = new ServerProxy();
        LoginRequest loginRequest = new LoginRequest("thisUserDoesntExist", "password");
        assertEquals("User does not exist",
                serverProxy.login("192.168.1.229", "8080", loginRequest).getMessage());
    }

    @Test
    public void getPerson() {
        ServerProxy serverProxy = new ServerProxy();
        assertEquals("Sheila",
                serverProxy.getPerson("192.168.1.229", "8080",
                        "3c0224bb-ff8f-4e25-aba0-47f1ffa80ec4",
                        "Sheila_Parker").getFirstName());
    }

    @Test
    public void invalidGetPerson() {
        ServerProxy serverProxy = new ServerProxy();
        assertEquals("No persons related to user found in database",
                serverProxy.getPerson("192.168.1.229", "8080",
                        "3c0224bb-ff8f-4e25-aba0-47f1ffa80ec4",
                        "does not exist").getPersonID());
    }

    @Test
    public void getAllPeople() {
        ServerProxy serverProxy = new ServerProxy();
        assertEquals("Sheila_Parker",
                serverProxy.getAllPeople("192.168.1.229", "8080",
                        "3c0224bb-ff8f-4e25-aba0-47f1ffa80ec4").getData()[0].getPersonID());
    }

    @Test
    public void invalidGetAllPeople() {
        ServerProxy serverProxy = new ServerProxy();
        assertEquals("No user found from provided authtoken",
                serverProxy.getAllPeople("192.168.1.229", "8080",
                        "wrongAuthToken").getMessage());
    }

    @Test
    public void getAllEvents() {
        ServerProxy serverProxy = new ServerProxy();
        assertEquals("Sheila_Parker",
                serverProxy.getAllEvents("192.168.1.229", "8080",
                        "3c0224bb-ff8f-4e25-aba0-47f1ffa80ec4").getData()[0].getPersonID());
    }

    @Test
    public void invalidGetAllEvents() {
        ServerProxy serverProxy = new ServerProxy();
        assertEquals("No user found from provided authtoken",
                serverProxy.getAllEvents("192.168.1.229", "8080",
                        "wrongAuthToken").getMessage());
    }
}