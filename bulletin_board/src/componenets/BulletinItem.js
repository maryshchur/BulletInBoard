import React, {Component} from "react";
import Card from '@material-ui/core/Card';
import CardContent from '@material-ui/core/CardContent';
import Typography from '@material-ui/core/Typography';
import Avatar from "@material-ui/core/Avatar";
import Button from "@material-ui/core/Button";
import ProfileForm from "./profile/ProfileForm";
import Link from "@material-ui/core/Link";

class BulletinItem extends Component {

    constructor(props) {
        super(props);
        this.state = {
            showModal: false
        };
        this.handleClick = this.handleClick.bind(this);
    };

    handleClick = (event) => {
        console.log(this.state.showModal);
        console.log("CLICK");
        this.setState({
            showModal: !this.state.showModal
        });
    };

    render() {
        const aa = (
            <ProfileForm data={this.props.item}/>
        );
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
                    {this.props.showAuthor &&
                    <Typography>
                        {/* eslint-disable-next-line react/jsx-no-undef */}
                        <Link href={`/profile/${this.props.item.user.id}`}>
                            <Button variant="contained"
                                    color="primary" >
                                Author :
                                {this.props.item.user.firstName} {this.props.item.user.lastName}
                            </Button>
                        </Link>
                       {/*{this.state.showModal && <ProfileForm data={this.props.item}/>}*/}
                    </Typography>}
                </CardContent>
            </Card>
        )
    }
}

export default BulletinItem;