import React, {Component} from "react";
import axios from "../utils/axios";
import Grid from "@material-ui/core/Grid";
import BulletinItem from "./BulletinItem";

class HomePage extends Component{

    state={
        bulletins: [],
    };
    getSubscriptionBulletins = ()=> {
        axios.get(`/profile/news`).then(
            response => {
                this.setState({
                    bulletins: response.data,
                })
            }
        )
    };
    componentDidMount() {
        this.getSubscriptionBulletins();
    }

    render() {
        return (
            <Grid style={{marginTop: 10}}>
                <Grid item xs/>
                <Grid container
                      justify="center">
                    {this.state.bulletins.map((item) =>
                        (<BulletinItem key={item.id}
                                       item={item}
                                       isNotUserPage={true}/>)
                    )}
                </Grid>
            </Grid>

        );
    }

}
export default HomePage;