import React, {Component} from "react";
import axios from "../utils/axios";

import {Link} from "react-router-dom";

import {Button, FormControl, FormHelperText, Grid, TextField} from '@material-ui/core';
import Typography from "@material-ui/core/Typography";
import Input from "@material-ui/core/Input";
import InputLabel from "@material-ui/core/InputLabel";

const gridStyles = {
    marginTop: 30
};
const textFieldStyles = {
    width: 300,
    minWidth: 100,
    maxWidth: 300
};

const buttomStyles = {
    marginTop: 20,
    marginBottom: 20
};
class RegistrationForm extends Component {

    state = {
        firstName: undefined,
        lastName: undefined,
        email: undefined,
        password: undefined,
        confirmationPassword: undefined,
        showPassword: false,
        showConfPassword: false,
        errorMessages: {}
    };
    isNotValid = () => {
        return (this.state.lastName === undefined ||
            this.state.password === undefined || this.state.firstName === undefined
            || this.state.email === undefined || this.state.confirmationPassword !== this.state.password);
    };

    onChangeFirstName = (event) => {
        this.setState({firstName: event.target.value});
    };
    onChangeLastName = (event) => {
        this.setState({lastName: event.target.value});
    };
    onChangeEmail = (event) => {
        this.setState({email: event.target.value});
    };
    onChangePassword = (event) => {
        this.setState({password: event.target.value});
    };
    onChangeConfirmationPassword = (event) => {
        let confirmationPassword = event.target.value;
        this.setState({confirmationPassword});
        if (confirmationPassword !== this.state.password) {
            let errors = {...this.state.errorMessages, ["confirmationPassword"]: "Passwords do not match"};
            this.setState({errorMessages: errors}, () => console.log(this.state));
        } else {
            let errors = {...this.state.errorMessages, ["confirmationPassword"]: undefined};
            this.setState({errorMessages: errors}, () => console.log(this.state));

        }
    };

    getData = () => {
        axios.post("/registration", this.state).then(response => {
            this.props.history.push("/");
        }, error => {
            let errors = {};
            error.response.data.forEach(err => {
                errors[[err.name]] = err.message;
            });
            this.setState({errorMessages: errors}, () => console.log(this.state));
        });
    };
    render() {
        return (
            <Grid container spacing={-2} direction='column' alignItems='center' alignContent='center'
                  style={gridStyles}
            >
                <Typography variant='h4' color='primary' paragraph='true'>Create Account</Typography>
                <Typography variant='subtitle1' color='textPrimary'>Please fill in all fields to create an
                    account</Typography>
                <TextField type="firstName" style={textFieldStyles} label="First name" onChange={this.onChangeFirstName}
                           helperText={this.state.errorMessages["firstName"]}
                           error={this.state.errorMessages["firstName"] !== undefined}
                />
                <TextField type="lastName" label="Last name" style={textFieldStyles} onChange={this.onChangeLastName}
                           helperText={this.state.errorMessages["lastName"]}
                           error={this.state.errorMessages["lastName"] !== undefined}
                />
                <TextField type="email" label="Email" style={textFieldStyles} onChange={this.onChangeEmail}
                           helperText={this.state.errorMessages["email"]}
                           error={this.state.errorMessages["email"] !== undefined}
                />
                <FormControl>
                    <InputLabel htmlFor="Password">Password</InputLabel>
                    <Input id="Password" type="password"
                           placeholder="Password"
                           style={textFieldStyles}
                           onChange={this.onChangePassword}
                           // endAdornment={
                           //     <InputAdornment position="end">
                           //         <IconButton
                           //             onClick={this.handleClickShowPassword}>
                           //             {this.state.showPassword ? <Visibility/> : <VisibilityOff/>}
                           //         </IconButton>
                           //     </InputAdornment>
                           // }
                    />
                    {this.state.errorMessages["password"] !== undefined &&
                    <FormHelperText style={textFieldStyles}
                                    htmlFor="Password"
                                    error={true}>
                        {this.state.errorMessages["password"]}
                    </FormHelperText>}
                </FormControl>
                <FormControl>
                    <InputLabel htmlFor="Repeat Password">Repeat Password</InputLabel>
                    <Input type="password"
                           placeholder="Repeat Password"
                           id={"Repeat Password"}
                           style={textFieldStyles}
                           onChange={this.onChangeConfirmationPassword}
                           helperText={this.state.errorMessages["confirmationPassword"]}
                           error={this.state.errorMessages["confirmationPassword"] !== undefined}
                           // endAdornment={
                           //     <InputAdornment position="end">
                           //         <IconButton
                           //             onClick={this.handleClickShowConfPassword}>
                           //             {this.state.showConfPassword ? <Visibility/> : <VisibilityOff/>}
                           //         </IconButton>
                           //     </InputAdornment>
                           // }
                    />
                    {this.state.errorMessages["confirmationPassword"] !== undefined &&
                    <FormHelperText style={textFieldStyles}
                                    htmlFor="Repeat Password"
                                    error={true}>
                        {this.state.errorMessages["confirmationPassword"]}
                    </FormHelperText>}
                </FormControl>
                <Button style={buttomStyles} variant="contained" color="primary" onClick={this.getData}
                        size="large"
                        disabled={this.isNotValid()}>Sign
                    up </Button>
                <div>
                    <Typography variant='subtitle1'>Already have an account? <Link to={"/"}>Sign in</Link></Typography>
                </div>
            </Grid>
        );
    }
}
export default RegistrationForm;