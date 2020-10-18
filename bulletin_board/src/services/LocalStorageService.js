import React from "react";
export const isUserLoggedIn = () => {
    return sessionStorage.getItem('Authorization') !== null;
};
export const logout = () => {
    sessionStorage.removeItem('Authorization');
    sessionStorage.removeItem('Id');
};
const LocalSessionStorageService = (function () {
    let _service;

    function _getService() {
        if (!_service) {
            _service = this;
            return _service
        }
        return _service
    }

    function _setAccessToken(accessToken) {
        sessionStorage.setItem('Authorization', accessToken);
    }

    function _setId(id) {
        sessionStorage.setItem('Id', id);
    }

    function _getAccessToken() {
        return sessionStorage.getItem('Authorization');
    }
    function _getId() {
        return sessionStorage.getItem('Id');
    }

    function _clearToken() {
        sessionStorage.removeItem('Authorization');
    }

    function _clearId() {
        sessionStorage.removeItem('Id');
    }

    return {
        getService: _getService,
        setId: _setId,
        setAccessToken: _setAccessToken,
        getId: _getId,
        getAccessToken: _getAccessToken,
        clearId: _clearId,
        clearToken: _clearToken
    }
})();


export default LocalSessionStorageService;