import React, {Component} from "react";
import axios from "../utils/axios";
import Grid from "@material-ui/core/Grid";
import BulletinItem from "./BulletinItem";
import CustomPagination from "./CustomPagination";

const paginationStyle = {
    padding: 20
};
const itemsNumber = 10;

class AllBulletin extends Component {
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
                <Grid item xs/>
                <Grid container
                      justify="center">
                    {this.state.bulletins.map((item) =>
                        (<BulletinItem key={item.id} getAllBulletins={() => this.getAllBulletins()}
                                       item={item}/>)
                    )}
                    <Grid container
                          style={paginationStyle}
                          justify="center">
                        <CustomPagination
                            activepage={this.state.activePage}
                            totalPages={this.state.totalPages}
                            itemsCountPerPage={this.state.itemsCountPerPage}
                            totalItemsCount={this.state.totalItemsCount}
                            onChange={this.handlePageChange}
                        />
                    </Grid>
                </Grid>

            </Grid>
        )
    }
}

export default AllBulletin;