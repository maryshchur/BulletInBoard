import React, {Component} from 'react';
import {FormControl, TextField} from "@material-ui/core";
import DialogContent from "@material-ui/core/DialogContent";
import DialogActions from "@material-ui/core/DialogActions";
import Button from "@material-ui/core/Button";
import axios from "../../utils/axios";
const textFieldStyles = {
    width: 300,
    minWidth: 100,
    maxWidth: 300
};
class EditProfile extends Component {
    state = {
        firstName: this.props.firstName,
        lastName:this.props.lastName,
        email: this.props.email,
        password: this.props.password,
        errorMessages: {}
    };

    //TODO here should be only firstName and lastName
    //TODO create possibility to change email separately(with redirection to login)
    //TODO create possibility to change password separately with entering current password
    editData=()=>{
        let data = {};
        data.firstName = this.state.firstName;
        data.lastName = this.state.lastName;
        data.email = this.state.email;
        data.password = this.state.password;
        axios.put(`/profile/${this.props.id}`,data).then(response=>{
            this.props.handleClose()
        }).catch(error => {
            let errors = {};
            this.setState({errorMessages: errors}
            )});
};
    onChangeEmail = (event) => {
        this.setState({email: event.target.value});
    };
    onChangeFirstName = (event) => {
        this.setState({firstName: event.target.value});
    };
    onChangeLastName = (event) => {
        this.setState({lastName: event.target.value});
    };
    onChangePassword = (event) => {
        this.setState({password: event.target.value});
    };
    componentDidMount() {

        console.log(this.state.email);
        console.log(this.props.email);
        console.log(this.props.firstName)
    }

    render() {
        return (
            <div>
                <DialogContent>
                    <TextField type="firstName" style={textFieldStyles} label="First name"
                               onChange={this.onChangeFirstName}
                               defaultValue={this.props.firstName}
                               helperText={this.state.errorMessages["firstName"]}
                               error={this.state.errorMessages["firstName"] !== undefined}
                    />
                    <TextField type="lastName" label="Last name" style={textFieldStyles}
                               onChange={this.onChangeLastName}
                               defaultValue={this.props.lastName}
                               helperText={this.state.errorMessages["lastName"]}
                               error={this.state.errorMessages["lastName"] !== undefined}
                    />
                    <TextField type="email" label="Email" style={textFieldStyles}
                               onChange={this.onChangeEmail}
                               defaultValue={this.props.email}
                               helperText={this.state.errorMessages["email"]}
                               error={this.state.errorMessages["email"] !== undefined}
                    />
                    <TextField type="password" label="Password" style={textFieldStyles}
                               onChange={this.onChangePassword}
                               defaultValue={this.props.password}
                               helperText={this.state.errorMessages["password"]}
                               error={this.state.errorMessages["password"] !== undefined}
                    />
                </DialogContent>
                <DialogActions>
                    <Button autoFocus onClick={this.props.handleClose} color="primary">
                        Cancel
                    </Button>
                    <Button onClick={this.editData} color="primary">
                        Save
                    </Button>
                </DialogActions>
            </div>
        );
    }
}
export default EditProfile;
