sap.ui.define([
    "sap/ui/core/mvc/Controller",
    "sap/ui/model/odata/v2/ODataModel"
], function (Controller, ODataModel) {
    "use strict";

    return Controller.extend("donald.controller.Main", {

        onInit: function () {

            var oModel = new ODataModel("/anubhav.svc/");
            this.getView().setModel(oModel);

        }

    });
});
