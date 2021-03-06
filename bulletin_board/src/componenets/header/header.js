import React from "react";
import {makeStyles} from '@material-ui/core/styles';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import Button from '@material-ui/core/Button';
import IconButton from '@material-ui/core/IconButton';
import MenuItem from '@material-ui/core/MenuItem';
import Menu from '@material-ui/core/Menu';
import MenuIcon from '@material-ui/icons/Menu';
import AccountCircle from '@material-ui/icons/AccountCircle';
import {Link} from 'react-router-dom';
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
    let isProfilePage = window.location.pathname === "/profile";

    const openProfile = event => {
        isProfilePage = true;
        window.location.href = "/profile";

    };
    const openAllBulletins = event => {
        window.location.href = "/all-bulletin";
        isProfilePage = false;
    };
    const openHome = event => {

    }
    const logoutUser = () => {
        logout();
        window.location.href = "/";
    };
    const [anchorEl, setAnchorEl] = React.useState(null);

    const handleClick = (event) => {
        setAnchorEl(event.currentTarget);
    };

    const handleClose = () => {
        setAnchorEl(null);
    };

    const userLoggedIn = (
        <div>
            {/* eslint-disable-next-line react/jsx-no-undef */}
            <IconButton
                aria-label="account of current user"
                aria-controls="menu-appbar"
                aria-haspopup="true"
                onClick={handleClick}
                color="inherit"
            >
                {/* eslint-disable-next-line react/jsx-no-undef */}
                <MenuIcon/>
            </IconButton>
            <Menu
                id="simple-menu"
                anchorEl={anchorEl}
                keepMounted
                open={Boolean(anchorEl)}
                onClose={handleClose}
            >
                <MenuItem ><Link to={"/home"}>Home</Link></MenuItem>
                <MenuItem > <Link to={"/all-bulletin"}>All Bulletins</Link></MenuItem>
                <MenuItem ><Link to={"/home/liked-bulletins"}>Liked Bulletins</Link></MenuItem>
            </Menu>
            <Link to={"/profile"}>
                <IconButton
                    aria-label="account of current user"
                    aria-controls="menu-appbar"
                    aria-haspopup="true"
                    // disabled={isProfilePage}
                    onClick={openProfile}
                    color="inherit" style={{color: '#FFF'}}
                >
                    <AccountCircle/>
                </IconButton></Link>
            {/*<Link to={"/all-bulletin"}><Button color="inherit" style={{color: '#FFF'}}*/}
            {/*    // disabled={!isProfilePage}*/}
            {/*>All Bulletins</Button>*/}
            {/*</Link>*/}

            <Link to={"/home"}><Button color="inherit" style={{color: '#FFF'}}
                // disabled={!isProfilePage}
            >Home</Button>
            </Link>

            <Button color="inherit" onClick={logoutUser}>Logout</Button>

        </div>
    );

    const userNotLoggedIn = (
        <div>
            <Link to="/"><Button style={{color: '#FFF'}}>Sign In</Button></Link>
            <Link to="/registration"><Button style={{color: '#FFF'}}>Sign Up</Button></Link>
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
            <AppBar position="static" style={{background: '#2185f6'}}>
                <Toolbar className={classes.title}>
                    {headerLinks}
                </Toolbar>
            </AppBar>
        </div>
    );
};

export default Header;