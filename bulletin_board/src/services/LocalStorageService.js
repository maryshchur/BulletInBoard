import React from "react";
export const isUserLoggedIn = () => {
    return sessionStorage.getItem('Authorization') !== null;
};
export const logout = () => {
    sessionStorage.removeItem('Authorization');
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

    function _getAccessToken() {
        return sessionStorage.getItem('Authorization');
    }

    function _clearToken() {
        sessionStorage.removeItem('Authorization');
    }

    return {
        getService: _getService,
        setAccessToken: _setAccessToken,
        getAccessToken: _getAccessToken,
        clearToken: _clearToken
    }
})();


export default LocalSessionStorageService;