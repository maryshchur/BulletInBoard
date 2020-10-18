import React from "react";
import { makeStyles } from '@material-ui/core/styles';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import Button from '@material-ui/core/Button';
import IconButton from '@material-ui/core/IconButton';
import MenuItem from '@material-ui/core/MenuItem';
import Menu from '@material-ui/core/Menu';
import AccountCircle from '@material-ui/icons/AccountCircle';
import { Link } from 'react-router-dom';
import {isUserLoggedIn, logout} from "../../services/LocalStorageService";

const Header = (props) => {

    const useStyles = makeStyles(theme => ({
        root: {
            flexGrow: 1,
        },
        title: {
            justifyContent: "space-between",
        }
    }));

    const classes = useStyles();

    //TODO change using withRouting or smth like that
    let isProfilePage=window.location.pathname === "/profile";

    const openProfile = event => {
        isProfilePage=true;
         window.location.href="/profile";

    };
    const openAllBulletins= event => {
        window.location.href="/all-bulletin";
        isProfilePage=false;
    };
    const logoutUser = () => {
        logout();
        window.location.href = "/";
    };


    const userLoggedIn = (
        <div>
            <IconButton
                aria-label="account of current user"
                aria-controls="menu-appbar"
                aria-haspopup="true"
                // disabled={isProfilePage}
                onClick={openProfile}
                color="inherit"
            >
                <AccountCircle />
            </IconButton>
            <Button color="inherit"
                    // disabled={!isProfilePage}
                    onClick={openAllBulletins}>All Bulletins</Button>
            <Button color="inherit" onClick={logoutUser}>Logout</Button>

        </div>
    );

    const userNotLoggedIn = (
        <div>
            <Link to="/"><Button style={{ color: '#FFF' }}>Sign In</Button></Link>
            <Link to="/registration"><Button style={{ color: '#FFF' }}>Sign Up</Button></Link>
        </div>
    );


    let headerLinks;
    if (isUserLoggedIn()) {
        headerLinks = userLoggedIn;
    } else {
        headerLinks = userNotLoggedIn;
    }

    return (
        <div className={classes.root}>
            <AppBar position="static" style={{ background: '#2185f6' }}>
                <Toolbar className={classes.title}>
                    {headerLinks}
                </Toolbar>
            </AppBar>
        </div>
    );
};

export default Header;