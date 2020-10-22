import React, {Component} from "react";
import axios from "../utils/axios";
import Grid from "@material-ui/core/Grid";
import BulletinItem from "./BulletinItem";

class LikedBulletinsPage extends Component {
    state={
        bulletins: [],
    };
    getLikedBulletins = ()=> {
        axios.get(`/profile/liked`).then(
            response => {
                this.setState({
                    bulletins: response.data,
                })
            }
        )
    };
    componentDidMount() {
        this.getLikedBulletins();
    }

    render() {
        return (
            <Grid style={{marginTop: 10}}>
                <Grid item xs/>
                {this.state.bulletins.length > 0 &&
                <Grid container
                      justify="center">
                    {this.state.bulletins.map((item) =>
                        (<BulletinItem key={item.id}
                                       item={item}
                                       likedByCurrentUser={true}
                                       getAllBulletins={() => this.getLikedBulletins()}
                                       isNotUserPage={true}/>)
                    )}
                </Grid>
                ||
                <div>You have not liked any bulletin</div>
                }
            </Grid>

        );
    }
}
export default LikedBulletinsPage;