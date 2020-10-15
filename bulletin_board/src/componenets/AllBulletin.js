import React, {Component} from "react";
import Grid from "@material-ui/core/Grid";
import BulletinItem from "./BulletinItem";
import CustomPagination from "./CustomPagination";

const paginationStyle = {
    padding: 20
};

class AllBulletin extends Component {

    render() {
        return (
            <Grid style={{marginTop: 10}}>
                <Grid item xs/>
                <Grid container
                      justify="center">
                    {this.props.bulletins.map((item) =>
                        (<BulletinItem key={item.id}
                                       item={item}
                                       showAuthor={this.props.showAuthor}/>)
                    )}
                    <Grid container
                          style={paginationStyle}
                          justify="center">
                        <CustomPagination
                            activepage={this.props.activePage}
                            totalPages={this.props.totalPages}
                            itemsCountPerPage={this.props.itemsCountPerPage}
                            totalItemsCount={this.props.totalItemsCount}
                            onChange={this.props.handlePageChange}
                        />
                    </Grid>
                </Grid>

            </Grid>
        )
    }
}

export default AllBulletin;