package pl.com.bottega.ecommerce.sales.domain.invoicing;

import pl.com.bottega.ecommerce.sharedkernel.Money;

import java.math.BigDecimal;

public class TaxByCountry {
    BigDecimal valueTax[];
    String descriptionTAX[];


    public static TaxByCountry createTaxByCountry(String country)
    {
        TaxByCountry taxByCountry = new TaxByCountry();
        if(country.equals("poland")) {
            taxByCountry.valueTax[0] = BigDecimal.valueOf(0.05);
            taxByCountry.descriptionTAX[0] = "5% (D)";
            taxByCountry.valueTax[1] = BigDecimal.valueOf(0.07);
            taxByCountry.descriptionTAX[1] = "7% (F)";
            taxByCountry.valueTax[2] = BigDecimal.valueOf(0.23);
            taxByCountry.descriptionTAX[1] = "23%";
        }
        return taxByCountry;
    }

    public Tax calculateTax(RequestItem item)
    {
        BigDecimal ratio = null;
        String desc = null;
        switch (item.getProductData().getType()) {
            case DRUG:
                ratio = this.valueTax[0] ;
                desc = this.descriptionTAX[0];
                break;
            case FOOD:
                ratio =  this.valueTax[1];
                desc = this.descriptionTAX[1];
                break;
            case STANDARD:
                ratio = this.valueTax[2];
                desc = this.descriptionTAX[2];
                break;

            default:
                throw new IllegalArgumentException(item.getProductData().getType() + " not handled");
        }

        Money taxValue = item.getTotalCost().multiplyBy(ratio);
        return  new Tax(taxValue, desc);

    }

    private TaxByCountry()
    {
        valueTax = new BigDecimal[3];
        descriptionTAX = new String[3];
    }
}