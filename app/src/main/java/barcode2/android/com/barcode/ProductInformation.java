package barcode2.android.com.barcode;

/**
 * Created by Deividas on 2015-10-07.
 */
public class ProductInformation {

    private int barcode;
    private int picture_id;
    private int foodAdditives_id;
    private int basic_info_id;

    public ProductInformation(int picture_id, int barcode, int foodAdditives_id, int basic_info_id){
        this.picture_id = picture_id;
        this.barcode = barcode;
        this.foodAdditives_id = foodAdditives_id;
        this.basic_info_id = basic_info_id;
    }

    public int getPictureID() {
        return picture_id;
    }

    public int getBarcode() {
        return barcode;
    }

    public int getFoodAdditivesID() {
        return foodAdditives_id;
    }

    public int getBasicInfoID(){
        return basic_info_id;
    }
}
