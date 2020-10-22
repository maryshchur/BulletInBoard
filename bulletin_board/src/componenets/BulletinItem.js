import React, {Component} from "react";
import Card from '@material-ui/core/Card';
import CardContent from '@material-ui/core/CardContent';
import Typography from '@material-ui/core/Typography';
import Avatar from "@material-ui/core/Avatar";
import Button from "@material-ui/core/Button";
import Link from "@material-ui/core/Link";
import DeleteIcon from "@material-ui/icons/Delete";
import axios from "../utils/axios";
import LocalSessionStorageService from "../services/LocalStorageService";
import FavoriteIcon from '@material-ui/icons/Favorite';
import FaceIcon from '@material-ui/icons/Face';
import FavoriteBorderIcon from '@material-ui/icons/Favorite';
const localStorageService = LocalSessionStorageService.getService();

class BulletinItem extends Component {
    delete = () => {
        axios.delete(`/bulletin/${this.props.item.id}`).then(
            response => {  this.props.getAllBulletins();
            }).catch(error => {
        });
    };

    like = () => {
        axios.put(`/bulletin/${this.props.item.id}`).then(
            response => {  this.props.getAllBulletins();
            }).catch(error => {
        });
    };

    render() {
        let likesButton;
        const ifCurrentUser=(
            <div>
            <FavoriteIcon/>
        {this.props.item.amountOfLikes}
            </div>
        );
        let icon;
        //TODO FavoriteIcon and FavoriteBorderIcon looks the same WTF?
        if (this.props.item.likedByCurrentUser || this.props.likedByCurrentUser) icon=<FavoriteIcon/>
        else icon= <FavoriteBorderIcon color="disabled"/>
        const ifOtherUser=(
            <div>
                <Button
                    size="small"
                    style={{
                        marginLeft: "10px"
                    }}
                    startIcon={icon}
                    onClick={this.like}/>
                {this.props.item.amountOfLikes}
            </div>
        );
        if(this.props.item.user.id==localStorageService.getId()) likesButton=ifCurrentUser;
        else likesButton=ifOtherUser;
        return (
            <Card>
                <CardContent>
                    <Typography gutterBottom variant="h5" component="h2">
                        {this.props.item.title}
                    </Typography>
                    <Avatar variant="square" style={{height: 200, width: 450}}
                            src={this.props.item.image}

                    />
                    <Typography>
                        {this.props.item.description}
                    </Typography>
                    <Typography>
                        Added at {this.props.item.addedDate}

                    </Typography>
                    {this.props.item.user.id==localStorageService.getId() && !this.props.isNotUserPage
                    && <Button
                        variant="contained"
                        color="primary"
                        size="small"
                        style={{
                            marginLeft: "10px"
                        }}
                        startIcon={<DeleteIcon/>}
                        onClick={this.delete}
                    />}
                    {likesButton}
                    {this.props.isNotUserPage &&
                    <Typography>
                        <Link href={`/profile/${this.props.item.user.id}`}>
                            <Button variant="contained"
                                    color="primary">
                                Author :
                                {this.props.item.user.firstName} {this.props.item.user.lastName}
                            </Button>
                        </Link>
                    </Typography>}
                </CardContent>
            </Card>
        )
    }
}

export default BulletinItem;