package com.example.skilledanswers_d1.searchdeal.Models;

import android.graphics.Bitmap;

import java.util.Comparator;

/**
 * Created by SkilledAnswers-D1 on 30-03-2016.
 */
public class OffersModel {
    private String _company;
    private String _productName;
    private String _productImage;
    private String _saleprice;
    private String _actualPrice;
    private String _saleDetails;
    private Bitmap _companyLogo;
    private String _discount;


    public OffersModel(String _company, String _productName, String _productImage, String _saleprice, String _actualPrice, String _saleDetails, Bitmap _companyLogo, String _discount) {
        this._company = _company;
        this._productName = _productName;
        this._productImage = _productImage;
        this._saleprice = _saleprice;
        this._actualPrice = _actualPrice;
        this._saleDetails = _saleDetails;
        this._companyLogo = _companyLogo;
        this._discount = _discount;

    }

    public OffersModel() {

    }


    public String get_company() {
        return _company;
    }

    public void set_company(String _company) {
        this._company = _company;
    }

    public String get_productName() {
        return _productName;
    }

    public void set_productName(String _productName) {
        this._productName = _productName;
    }

    public String get_productImage() {
        return _productImage;
    }

    public void set_productImage(String _productImage) {
        this._productImage = _productImage;
    }

    public String get_saleprice() {
        return _saleprice;
    }

    public void set_saleprice(String _saleprice) {
        this._saleprice = _saleprice;
    }

    public String get_actualPrice() {
        return _actualPrice;
    }

    public void set_actualPrice(String _actualPrice) {
        this._actualPrice = _actualPrice;
    }

    public String get_saleDetails() {
        return _saleDetails;
    }

    public void set_saleDetails(String _saleDetails) {
        this._saleDetails = _saleDetails;
    }

    public Bitmap get_companyLogo() {
        return _companyLogo;
    }

    public void set_companyLogo(Bitmap _companyLogo) {
        this._companyLogo = _companyLogo;
    }

    public String get_discount() {
        return _discount;
    }

    public void set_discount(String _discount) {
        this._discount = _discount;
    }


    // compartor for sorting by price(low to high) and discount (low to high)
    //// price
    public static Comparator<OffersModel> priceComparatorLowToHigh = new Comparator<OffersModel>() {
        @Override
        public int compare(OffersModel lhs, OffersModel rhs) {
                int LhsProdPrice = Integer.parseInt(lhs.get_saleprice());
                int RhsprodPrice = Integer.parseInt(rhs.get_saleprice());
            return LhsProdPrice - RhsprodPrice;
        }
    };
    public static Comparator<OffersModel> priceComparatorHighToLow = new Comparator<OffersModel>() {
        @Override
        public int compare(OffersModel lhs, OffersModel rhs) {
            int LhsProdPrice = Integer.parseInt(lhs.get_saleprice());
            int RhsprodPrice = Integer.parseInt(rhs.get_saleprice());
            return RhsprodPrice-LhsProdPrice;
        }
    };
    /// discount
    public static Comparator<OffersModel> discountComparatorLowToHigh = new Comparator<OffersModel>() {
        @Override
        public int compare(OffersModel lhs, OffersModel rhs) {
            int LhsProddiscount = Integer.parseInt(lhs.get_discount());
            int Rhsproddiscount = Integer.parseInt(rhs.get_discount());
            return LhsProddiscount - Rhsproddiscount;
        }
    };
    public static Comparator<OffersModel> discountComparatorHighToLow = new Comparator<OffersModel>() {
        @Override
        public int compare(OffersModel lhs, OffersModel rhs) {
            int LhsProddiscount = Integer.parseInt(lhs.get_discount());
            int Rhsproddiscount = Integer.parseInt(rhs.get_discount());
            return Rhsproddiscount - LhsProddiscount;
        }
    };


}
