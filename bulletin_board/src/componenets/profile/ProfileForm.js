import axios from "../../utils/axios";
import React, {Component} from "react";
import Card from "@material-ui/core/Card";
import CardContent from "@material-ui/core/CardContent";
import {Alert} from "@material-ui/lab";
import Typography from "@material-ui/core/Typography";

import {CssBaseline, Grid} from "@material-ui/core";
import CreateBulletin from "../bulletin/CreateBulletin";
import DialogTitle from "@material-ui/core/DialogTitle";
import Dialog from "@material-ui/core/Dialog";
import EditIcon from "@material-ui/icons/Edit";
import AddIcon from '@material-ui/icons/Add';
import EditProfile from "./EditProfile";
import Button from "@material-ui/core/Button";

const style = {
    marginTop: 50,
    height: 700,
    wight: 500
};

class ProfileForm extends Component {

    state = {
        id: undefined,
        firstName: "",
        lastName: "",
        email: "",
        password: undefined,
        errorMessage: null,
        errorMessages: {},
        err: undefined,
        reLogin: '',
        openDialogChangeData: false,
        openDialogAddBulletin: false
    };

    getData = () => {
        axios.get(`/profile`).then(
            response => {
                console.log(response.data);
                let data = response.data;
                this.setState({
                    id: data.id,
                    firstName: data.firstName,
                    lastName: data.lastName,
                    email: data.email,
                    password: data.password
                })
            }).catch(error => {
            console.dir(error.response.data);

        })
    };

    componentDidMount() {
        this.getData();
    }

    handleOpenDialogChangeData = () => {
        console.log(this.state.firstName);
        this.setState({openDialogChangeData: true});
    };
    handleCloseDialogChangeData = () => {
        this.setState({openDialogChangeData: false});
        this.getData()
    };
    handleOpenDialogAddBulletin = () => {
        this.setState({openDialogAddBulletin: true});
    };
    handleCloseDialogAddBulletin = () => {
        this.setState({openDialogAddBulletin: false});
    };

    render() {
        return (
            <Grid container>
                <Grid item xs={1}/>
                <Grid xs={12} sm={10}>
                    <Card
                        style={style}
                    >
                        <CardContent>
                            <CssBaseline/>
                            {this.state.err && <Alert severity="error">{this.state.err}</Alert>}
                            <Grid container justify={"space-evenly"}>
                                <Grid xl={12} xs={12}>
                                    <Typography variant="h3" component="h3" style={{marginTop: 10}}
                                                align={"center"} color="textPrimary">
                                        {this.state.firstName} {this.state.lastName}
                                    </Typography>
                                </Grid>
                                <Grid xs={12} sm={12}
                                >

                                    <Typography variant="h5" color="textSecondary" component="p"
                                                style={{
                                                    position: "relative",
                                                    left: "25px"
                                                }}
                                    >
                                        {this.state.email}
                                    </Typography>
                                </Grid>
                            </Grid>
                            <Button
                                align={"left"}
                                variant="contained"
                                color="primary"
                                size="small"
                                style={{
                                    marginRight: "10px"
                                }}
                                onClick={this.handleOpenDialogChangeData}
                                startIcon={<EditIcon/>}>
                                Edit profile</Button>
                            <Dialog
                                open={this.state.openDialogChangeData}
                                onClose={this.handleCloseDialogChangeData}
                                aria-labelledby="responsive-dialog-title"
                            >
                                <DialogTitle id="responsive-dialog-title">Change profile data</DialogTitle>
                                <EditProfile handleClose={this.handleCloseDialogChangeData}
                                             firstName={this.state.firstName}
                                             lastName={this.state.lastName}
                                             email={this.state.email}
                                             password={this.state.password}
                                             id={this.state.id}/>
                            </Dialog>
                            <Button
                                align={"left"}
                                variant="contained"
                                color="primary"
                                size="small"
                                style={{
                                    marginRight: "10px"
                                }}
                                onClick={this.handleOpenDialogAddBulletin}
                                startIcon={<AddIcon/>}>
                                Add bulletin</Button>
                            <Dialog
                                open={this.state.openDialogAddBulletin}
                                onClose={this.handleCloseDialogAddBulletin}
                                aria-labelledby="responsive-dialog-title"
                            >
                                <DialogTitle id="responsive-dialog-title">Add bulletin</DialogTitle>
                                <CreateBulletin handleClose={this.handleCloseDialogAddBulletin}
                                             firstName={this.state.firstName}
                                             lastName={this.state.lastName}
                                             email={this.state.email}
                                             password={this.state.password}
                                             id={this.state.id}/>
                            </Dialog>
                        </CardContent>

                    </Card>
                </Grid>
            </Grid>
        );
    }
}

export default ProfileForm;