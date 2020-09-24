import React, {Component} from "react";
import Card from '@material-ui/core/Card';
import CardContent from '@material-ui/core/CardContent';
import Typography from '@material-ui/core/Typography';
import Avatar from "@material-ui/core/Avatar";

class BulletinItem extends Component {

    render() {
        console.log(this.props.item.addedDate);
        return (
            <Card>
                <CardContent>
                    <Typography gutterBottom variant="h5" component="h2" >
                        {this.props.item.title}
                    </Typography>
                    <Avatar variant="square" style={{height: 200, width: 450}}
                            src={"http://localhost:8080/upload/" + this.props.item.image}
                    />
                    <Typography>
                        {this.props.item.description}
                    </Typography>
                    <Typography>
                        Added at  {this.props.item.addedDate}
                    </Typography>
                    <Typography>
                        Author: {this.props.item.user.firstName} {this.props.item.user.lastName}
                    </Typography>
                </CardContent>
            </Card>
        )
    }
}

export default BulletinItem;