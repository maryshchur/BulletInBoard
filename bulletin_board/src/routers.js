import LoginForm from "./componenets/LoginForm/loginForm";
import React, {Component} from "react";
import {BrowserRouter} from "react-router-dom";
import {Route, Switch} from "react-router";
import RegistrationForm from "./componenets/RegistrationForm";
import Header from "./componenets/header/header";
import ProfileForm from "./componenets/profile/ProfileForm";
import MainAllBulletinsPage from "./componenets/MainAllBulletinsPage";
import HomePage from "./componenets/HomePage";
import LikedBulletinsPage from "./componenets/LikedBulletinsPage";

class Routers extends Component {

    render() {
        return (
            <BrowserRouter>
                <Header/>
                <Switch>
                    <Route path="/" exact component={LoginForm} />
                    <Route path="/registration"  component={RegistrationForm} />
                    <Route path="/profile" exact component={ProfileForm} />
                    <Route path="/profile/:id"  component={ProfileForm} />
                    <Route path="/home"  exact component={HomePage} />
                    <Route path="/home/liked-bulletins"  component={LikedBulletinsPage} />
                    <Route path="/all-bulletin"  component={MainAllBulletinsPage} />
                </Switch>
            </BrowserRouter>
        );
    }
}

export default Routers;