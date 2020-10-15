import axios from "../../utils/axios";
import React, {Component} from "react";
import Card from "@material-ui/core/Card";
import CardContent from "@material-ui/core/CardContent";
import Typography from "@material-ui/core/Typography";

import { Grid} from "@material-ui/core";
import CreateBulletin from "../bulletin/CreateBulletin";
import DialogTitle from "@material-ui/core/DialogTitle";
import Dialog from "@material-ui/core/Dialog";
import EditIcon from "@material-ui/icons/Edit";
import AddIcon from '@material-ui/icons/Add';
import EditProfile from "./EditProfile";
import Button from "@material-ui/core/Button";
import AllBulletin from "../AllBulletin";

const style = {
    marginTop: 50,
    wight: 500
};
const itemsNumber = 2;

class ProfileForm extends Component {

    state = {
        id: undefined,
        firstName: "",
        lastName: "",
        email: "",
        password: undefined,
        openDialogChangeData: false,
        openDialogAddBulletin: false,
        bulletins: [],
        activePage: 1,
        totalItemsCount: 1,
        totalPages: 0,
        itemsCountPerPage: 0,
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

    getAllUserBulletins = (pageNumber) => {
        axios.get(`/profile/my-bulletins?page=${pageNumber}&pageSize=${itemsNumber}`).then(
            response => {
                let totalPages = response.data.totalPages;
                let itemsCountPerPage = response.data.numberOfElements;
                let totalItemsCount = response.data.totalElements;
                let data = response.data.content;
                this.setState({
                    bulletins: data,
                    totalPages: totalPages,
                    itemsCountPerPage: itemsCountPerPage,
                    totalItemsCount: totalItemsCount
                })
            }
        )
    };
    handlePageChange = (event, pageNumber) => {
        this.setState({activePage: pageNumber});
        this.getAllUserBulletins(pageNumber)
    };

    componentDidMount() {
        this.getData();
        this.getAllUserBulletins(this.state.activePage)
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
                        <Typography variant="h5" color="textSecondary" component="p">
                            My bulletins
                        </Typography>
                        <AllBulletin bulletins={this.state.bulletins}
                                     showAuthor={false}
                                     activepage={this.state.activePage}
                                     totalPages={this.state.totalPages}
                                     itemsCountPerPage={this.state.itemsCountPerPage}
                                     totalItemsCount={this.state.totalItemsCount}
                                     handlePageChange={this.handlePageChange}
                        />

                    </Card>
                </Grid>
            </Grid>
        );
    }
}

export default ProfileForm;