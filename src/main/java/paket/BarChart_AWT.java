package paket;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel; 
import org.jfree.chart.JFreeChart; 
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset; 
import org.jfree.data.category.DefaultCategoryDataset; 
import org.jfree.ui.ApplicationFrame; 
import org.jfree.ui.RefineryUtilities; 

public class BarChart_AWT extends ApplicationFrame {
   private String imeGrafa = "";
   public BarChart_AWT( String applicationTitle , String chartTitle ) {
      super( applicationTitle ); 
      imeGrafa = chartTitle;
     
      JFreeChart barChart = ChartFactory.createBarChart(
         chartTitle,           
         "Vrsta",            
         imeGrafa,            
         createDataset(),          
         PlotOrientation.VERTICAL,           
         true, true, false);
         
      ChartPanel chartPanel = new ChartPanel( barChart );        
      chartPanel.setPreferredSize(new java.awt.Dimension( 560 , 367 ) );        
      setContentPane( chartPanel ); 
   }
   
   private CategoryDataset createDataset( ) {
	   final String org = "Original";        
	      final String alloc = "Allocation";
	      final String adaBoost = "AdaBoost";
	      final String bagg = "Bagging";
	      final String j48 = "J48";        
	      final String naiv = "NaiveBayes";        
	      final String smo = "SMO";        
	      final String ibk = "IBk";        
	      final String oner = "OneR";   
	      final String nbtree = "NBTree";      
      final DefaultCategoryDataset dataset = 
      new DefaultCategoryDataset( );  

      //crtanje grafa
      if(imeGrafa.contentEquals("Podaci Fmeasure")) {
    	  System.out.println("FMEASURE");
    	  dataset.addValue( KlasaZaOsnovnoRac.rezultati[3].fscore , org, nbtree );        
          dataset.addValue( MajorityMin.rezultati[3].fscore /5 , alloc , nbtree );
          dataset.addValue( AdaBoost.rezultati[3].fscore, adaBoost , nbtree );
          dataset.addValue( Baging.rezultati[3].fscore, bagg , nbtree );
                  
          dataset.addValue( KlasaZaOsnovnoRac.rezultati[0].fscore , org, j48 );        
          dataset.addValue( MajorityMin.rezultati[0].fscore /5 , alloc , j48 );  
          dataset.addValue( AdaBoost.rezultati[0].fscore, adaBoost , j48 );
          dataset.addValue( Baging.rezultati[0].fscore, bagg , j48 );
          
          dataset.addValue( KlasaZaOsnovnoRac.rezultati[1].fscore , org, naiv );        
          dataset.addValue( MajorityMin.rezultati[1].fscore /5, alloc , naiv );
          dataset.addValue( AdaBoost.rezultati[1].fscore, adaBoost , naiv );
          dataset.addValue( Baging.rezultati[1].fscore, bagg , naiv );
          
          dataset.addValue( KlasaZaOsnovnoRac.rezultati[5].fscore , org, ibk );        
          dataset.addValue( MajorityMin.rezultati[5].fscore / 5, alloc , ibk );  
          dataset.addValue( AdaBoost.rezultati[5].fscore, adaBoost , ibk );
          dataset.addValue( Baging.rezultati[5].fscore, bagg , ibk );
          
          dataset.addValue( KlasaZaOsnovnoRac.rezultati[4].fscore , org, oner );        
          dataset.addValue( MajorityMin.rezultati[4].fscore / 5  , alloc , oner );
          dataset.addValue( AdaBoost.rezultati[4].fscore, adaBoost , oner );
          dataset.addValue( Baging.rezultati[4].fscore, bagg , oner );
       
          dataset.addValue( KlasaZaOsnovnoRac.rezultati[2].fscore , org, smo );        
          dataset.addValue( MajorityMin.rezultati[2].fscore / 5 , alloc , smo ); 
          dataset.addValue( AdaBoost.rezultati[2].fscore, adaBoost , smo );
          dataset.addValue( Baging.rezultati[2].fscore, bagg , smo );
      }else{
    	  System.out.println("FMEasdasdASURE");
    	  dataset.addValue( KlasaZaOsnovnoRac.rezultati[3].accuracy , org, nbtree );        
          dataset.addValue( MajorityMin.rezultati[3].accuracy /5 , alloc , nbtree );
          dataset.addValue( AdaBoost.rezultati[3].accuracy, adaBoost , nbtree );
          dataset.addValue( Baging.rezultati[3].accuracy, bagg , nbtree );
                  
          dataset.addValue( KlasaZaOsnovnoRac.rezultati[0].accuracy, org, j48 );        
          dataset.addValue( MajorityMin.rezultati[0].accuracy /5 , alloc , j48 );  
          dataset.addValue( AdaBoost.rezultati[0].accuracy, adaBoost , j48 );
          dataset.addValue( Baging.rezultati[0].accuracy, bagg , j48 );
          
          dataset.addValue( KlasaZaOsnovnoRac.rezultati[1].accuracy, org, naiv );        
          dataset.addValue( MajorityMin.rezultati[1].accuracy /5, alloc , naiv );
          dataset.addValue( AdaBoost.rezultati[1].accuracy, adaBoost , naiv );
          dataset.addValue( Baging.rezultati[1].accuracy, bagg , naiv );
          
          dataset.addValue( KlasaZaOsnovnoRac.rezultati[5].accuracy, org, ibk );        
          dataset.addValue( MajorityMin.rezultati[5].accuracy / 5, alloc , ibk );  
          dataset.addValue( AdaBoost.rezultati[5].accuracy, adaBoost , ibk );
          dataset.addValue( Baging.rezultati[5].accuracy, bagg , ibk );
          
          dataset.addValue( KlasaZaOsnovnoRac.rezultati[4].accuracy, org, oner );        
          dataset.addValue( MajorityMin.rezultati[4].accuracy / 5  , alloc , oner );
          dataset.addValue( AdaBoost.rezultati[4].accuracy, adaBoost , oner );
          dataset.addValue( Baging.rezultati[4].accuracy, bagg , oner );
       
          dataset.addValue( KlasaZaOsnovnoRac.rezultati[2].accuracy, org, smo );        
          dataset.addValue( MajorityMin.rezultati[2].accuracy/ 5 , alloc , smo ); 
          dataset.addValue( AdaBoost.rezultati[2].accuracy, adaBoost , smo );
          dataset.addValue( Baging.rezultati[2].accuracy, bagg , smo );
      }
      

              

      return dataset; 
   }}
