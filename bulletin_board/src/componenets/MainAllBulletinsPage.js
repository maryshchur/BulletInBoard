import React, {Component} from "react";
import axios from "../utils/axios";
import Grid from "@material-ui/core/Grid";
import AllBulletin from "./AllBulletin";

const itemsNumber = 10;

class MainAllBulletinsPage extends Component {
    state = {
        bulletins: [],
        activePage: 1,
        totalItemsCount: 1,
        totalPages: 0,
        itemsCountPerPage: 0,
    };
    getAllBulletins = (pageNumber) => {
        axios.get(`/bulletins?page=${pageNumber}&pageSize=${itemsNumber}`).then(
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
        this.getAllBulletins(pageNumber)
    };

    componentDidMount() {
        this.getAllBulletins(this.state.activePage);
    };

    render() {
        return (
            <Grid style={{marginTop: 10}}>
                <AllBulletin bulletins={this.state.bulletins}
                             isNotUserPage={true}
                              getAllBulletins={() => this.getAllBulletins(this.state.activePage)}
                             activepage={this.state.activePage}
                             totalPages={this.state.totalPages}
                             itemsCountPerPage={this.state.itemsCountPerPage}
                             totalItemsCount={this.state.totalItemsCount}
                             handlePageChange={this.handlePageChange}
                />
            </Grid>
        );
    }
}

export default MainAllBulletinsPage;