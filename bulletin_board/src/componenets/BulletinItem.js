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
const localStorageService = LocalSessionStorageService.getService();

class BulletinItem extends Component {
    delete = () => {
        axios.delete(`/bulletin/${this.props.item.id}`).then(
            response => {
            }).catch(error => {
        });
    };

    render() {
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
                    />
                    }
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