import axios from "../../utils/axios";
import React, {Component, useCallback} from "react";
import Card from "@material-ui/core/Card";
import CardContent from "@material-ui/core/CardContent";
import Typography from "@material-ui/core/Typography";
import {Grid} from "@material-ui/core";
import CreateBulletin from "../bulletin/CreateBulletin";
import DialogTitle from "@material-ui/core/DialogTitle";
import Dialog from "@material-ui/core/Dialog";
import EditIcon from "@material-ui/icons/Edit";
import AddIcon from '@material-ui/icons/Add';
import EditProfile from "./EditProfile";
import Button from "@material-ui/core/Button";
import AllBulletin from "../AllBulletin";
import {matchPath} from "react-router";
import LocalSessionStorageService from "../../services/LocalStorageService";
const localStorageService = LocalSessionStorageService.getService();
const style = {
    marginTop: 50,
    wight: 500
};
const itemsNumber = 2;
let isMyProfilePage = window.location.pathname === "/profile" ;
let uriForGetBulletins;
let uriForProfileData;
let qqq = 1;

const match = matchPath("/profile", {
    path: `/profile/${qqq}`,
    exact: true,
    strict: false
});

class ProfileForm extends Component {

    state = {
        id: undefined,
        firstName: "",
        lastName: "",
        email: "",
        password: undefined,
        amountOfSubscribers: undefined,
        amountOfSubscriptions: undefined,
        openDialogChangeData: false,
        openDialogAddBulletin: false,
        bulletins: null,
        activePage: 1,
        totalItemsCount: 1,
        totalPages: 0,
        itemsCountPerPage: 0,
        isCurrentUserSubscriber:false
    };

    getData = () => {
        axios.get(`${uriForProfileData}`).then(
            response => {
                console.log(response.data);
                let data = response.data;
                this.setState({
                    id: data.id,
                    firstName: data.firstName,
                    lastName: data.lastName,
                    email: data.email,
                    password: data.password,
                    amountOfSubscribers: data.amountOfSubscribers,
                    amountOfSubscriptions: data.amountOfSubscriptions,
                    isCurrentUserSubscriber: data.currentUserSubscriber
                })
            }).catch(error => {
            console.dir(error.response);

        })
    };

    getAllUserBulletins = (pageNumber) => {
        axios.get(`${uriForGetBulletins}?page=${pageNumber}&pageSize=${itemsNumber}`).then(
            response => {
                let totalPages = response.data.totalPages;
                let itemsCountPerPage = response.data.numberOfElements;
                let totalItemsCount = response.data.totalElements;
                let dat = response.data.content;
                this.setState({
                    bulletins: dat,
                    totalPages: totalPages,
                    itemsCountPerPage: itemsCountPerPage,
                    totalItemsCount: totalItemsCount
                });
            }
        )
    };
    handlePageChange = (event, pageNumber) => {
        this.setState({activePage: pageNumber});
        this.getAllUserBulletins(pageNumber)
    };

    rightURI = () => {
        if (isMyProfilePage) {
            uriForGetBulletins = "/profile/my-bulletins";
            uriForProfileData = "/profile";
        } else {
            uriForGetBulletins = `/${this.props.match.params.id}/profile`;
            uriForProfileData = `/profile/user/${this.props.match.params.id}`;
        }
    };

    componentDidMount() {
        this.rightURI();
        this.getData();
        this.getAllUserBulletins(this.state.activePage);
    }

    handleOpenDialogChangeData = () => {
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
        this.getAllUserBulletins(this.state.activePage);
    };
    subscribe = () => {
        axios.put(`/profile/subscribe/${this.state.id}`).then(response => {
            this.getData();
        });
    };


    render() {
        let data;
        let data2;
        if (this.state.isCurrentUserSubscriber) data2="Unsubscribe";
        else data2="Subscribe";
        const ifAnoutherUserProfile = (
            <div>
                <Button
                    align={"left"}
                    variant="contained"
                    color="primary"
                    size="small"
                    style={{
                        marginRight: "10px"
                    }}
                    onClick={this.subscribe}
                >
                    {data2}
                </Button>
            </div>
        );
        const ifOwnProfile = (
            <div>
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
            </div>
        );
        if (isMyProfilePage || localStorageService.getId() ==this.state.id){
            data = ifOwnProfile;}
        else data = ifAnoutherUserProfile;
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
                                    <Typography variant="h5" color="textSecondary" component="p"
                                                style={{
                                                    position: "relative",
                                                    left: "25px"
                                                }}
                                    >
                                        Subscribers: {this.state.amountOfSubscribers}
                                        Subscription: {this.state.amountOfSubscriptions}
                                    </Typography>
                                    {data}
                                </Grid>
                            </Grid>
                        </CardContent>
                        {this.state.bulletins && <div>
                            <Typography
                                variant="h5"
                                component="p">
                                Bulletins :
                            </Typography>
                            <AllBulletin bulletins={this.state.bulletins}
                                         isNotUserPage={false}
                                         getAllBulletins={()=>this.getAllUserBulletins(this.state.activePage)}
                                         activepage={this.state.activePage}
                                         totalPages={this.state.totalPages}
                                         itemsCountPerPage={this.state.itemsCountPerPage}
                                         totalItemsCount={this.state.totalItemsCount}
                                         handlePageChange={this.handlePageChange}
                            />
                        </div>}

                    </Card>
                </Grid>
            </Grid>
        );
    }
}

export default ProfileForm;