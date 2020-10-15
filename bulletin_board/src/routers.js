import LoginForm from "./componenets/LoginForm/loginForm";
import React, {Component} from "react";
import {BrowserRouter} from "react-router-dom";
import {Route, Switch} from "react-router";
import RegistrationForm from "./componenets/RegistrationForm";
import Header from "./componenets/header/header";
import ProfileForm from "./componenets/profile/ProfileForm";
import MainAllBulletinsPage from "./componenets/MainAllBulletinsPage";

class Routers extends Component {

    render() {
        return (
            <BrowserRouter>
                <Header/>
                <Switch>
                    <Route path="/" exact component={LoginForm} />
                    <Route path="/registration"  component={RegistrationForm} />
                    <Route path="/profile"  component={ProfileForm} />
                    <Route path="/all-bulletin"  component={MainAllBulletinsPage} />
                </Switch>
            </BrowserRouter>
        );
    }
}

export default Routers;