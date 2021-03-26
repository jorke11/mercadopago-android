package com.jorgepinedo.mercadopago;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.mercadopago.android.px.core.MercadoPagoCheckout;
import com.mercadopago.android.px.model.Payment;
import com.mercadopago.android.px.model.exceptions.MercadoPagoError;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 100;
    private static final int RESULT_CANCELED = 101;
    Button button;

    MercadoPagoCheckout checkout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button=findViewById(R.id.button);

//        final MercadoPagoCheckout checkout=new MercadoPagoCheckout.Builder("TEST-2ef9627a-353c-48e4-8782-cde57e6f8875","397912362-72ede695-8bd1-43f1-8ddb-b6b78e07c055").build();
        checkout=new MercadoPagoCheckout.Builder("TEST-2ef9627a-353c-48e4-8782-cde57e6f8875","397912362-72ede695-8bd1-43f1-8ddb-b6b78e07c055").build();
        //checkout=new MercadoPagoCheckout.Builder("APP_USR-e75ef51f-b786-487b-80ca-b9b6d9b23949 rere","403128331-c91d4b3a-2d51-4346-b691-7097b3a8c844").build();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkout.startPayment(MainActivity.this,REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(requestCode == REQUEST_CODE){

            if(resultCode == MercadoPagoCheckout.PAYMENT_RESULT_CODE){
                final Payment payment = (Payment) data.getSerializableExtra(MercadoPagoCheckout.EXTRA_PAYMENT_RESULT);

                Log.d("MercadoPago",payment.getPaymentStatus());
                Toast.makeText(MainActivity.this,"Pagado "+payment.getPaymentStatus(),Toast.LENGTH_SHORT).show();
            }else if(resultCode == RESULT_CANCELED){
                if (data != null && data.getExtras() != null && data.getExtras().containsKey(MercadoPagoCheckout.EXTRA_ERROR)) {
                    final MercadoPagoError mercadoPagoError = (MercadoPagoError) data.getSerializableExtra(MercadoPagoCheckout.EXTRA_ERROR);

                    Log.d("MercadoPago Canceled",mercadoPagoError.getMessage());
                    Toast.makeText(MainActivity.this,"Pago Canceled"+mercadoPagoError.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }



        }
    }
}
