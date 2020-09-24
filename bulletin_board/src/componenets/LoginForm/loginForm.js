import React, {Component} from "react";
import TextField from "@material-ui/core/TextField";
import axios from '../../utils/axios';
import {Box, Button} from "@material-ui/core";
import LocalSessionStorageService from "../../services/LocalStorageService";
import Grid from "@material-ui/core/Grid";
import Link from "@material-ui/core/Link";
import CssBaseline from "@material-ui/core/CssBaseline";
import Container from "@material-ui/core/Container";
import Alert from "@material-ui/lab/Alert";

const localStorageService = LocalSessionStorageService.getService();

const style = {
    marginTop: 40
};
const formControl = {
    marginTop: 15,
    minWidth: 395,
};

class LoginForm extends Component {

    state = {
        email: undefined,
        password: undefined,
        errorMessage: ''
    };

    getData = () => {
        axios.post("/authentication", this.state).then(response => {
                localStorageService.setAccessToken(response.data);
            window.location.href = "/all-bulletin";
        }, error => {
            this.setState({errorMessage: error.response.data.message});
        })
    };

    onChangeEmail = (event) => {
        this.setState({
            email: event.target.value
        })
    };

    onChangePassword = (event) => {
        this.setState({
            password: event.target.value
        })
    };

    handleChange = name => event => {
        this.setState({
            ...this.state,
            [name]: event.target.value,
        });
    };


    render() {

        return (
            <Container component="main" maxWidth="xs" style={style}>
                <CssBaseline/>
                {this.state.errorMessage && <Alert severity="error">{this.state.errorMessage}</Alert>}
                <div>
                    <TextField
                        onChange={this.onChangeEmail}
                        variant="outlined"
                        margin="normal"
                        required
                        fullWidth
                        id="email"
                        label="Email Address"
                        name="email"
                        autoComplete="email"
                        autoFocus
                    />
                    <TextField
                        onChange={this.onChangePassword}
                        variant="outlined"
                        margin="normal"
                        required
                        fullWidth
                        name="password"
                        label="Password"
                        type="password"
                        id="password"
                        autoComplete="current-password"
                    />
                    <Button
                        type="submit"
                        fullWidth
                        variant="contained"
                        color="primary"
                        onClick={this.getData}
                    >Sign In
                    </Button>

                    <Box mt={3}>
                        <Grid container>
                            <Grid item>Don't have an account?
                                <Link href={"/registration"}>
                                    Sign Up
                                </Link>
                            </Grid>
                        </Grid>
                    </Box>
                </div>
            </Container>
        );
    }
}


export default LoginForm;