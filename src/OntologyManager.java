import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.reasoner.InferenceType;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.reasoner.structural.StructuralReasonerFactory;
import org.semanticweb.owlapi.util.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public  class OntologyManager {

    public OntologyManager()  {
    }

    public static void main(String args[]) throws OWLOntologyCreationException, IOException {

        try{

            OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
            File ontologyFile = new File("C:\\Users\\nesli\\Desktop\\Ontology\\ontologySource\\anlamsalagodevison.owl");
            OWLOntology programModel = manager.loadOntologyFromOntologyDocument(ontologyFile);
            System.out.println("Ontology yuklendi : "+programModel);
            startOntologyAddRule(manager, programModel);

        }catch(OWLOntologyCreationException | OWLOntologyStorageException ex){
            ex.printStackTrace();
        }

    }

    public static void startOntologyAddRule(OWLOntologyManager manager, OWLOntology programModel) throws OWLOntologyCreationException, IOException, OWLOntologyStorageException {
        OWLDataFactory factory = manager.getOWLDataFactory();
        IRI iri = IRI.create("http://www.semanticweb.org/neslihan/ontologies/2021/5/anlamsalagodevi#");


        PrefixManager pm =new DefaultPrefixManager(
                    "http://www.semanticweb.org/neslihan/ontologies/2021/5/anlamsalagodevi#");
            try{
                OWLClass classYonetmen = factory.getOWLClass(":Yonetmen",pm);
                OWLClass classYardimciYonetmen = factory.getOWLClass(":YardimciYonetmen",pm);

                OWLAxiom subClassAxiom2 = factory.getOWLSubClassOfAxiom(classYardimciYonetmen,classYonetmen);

                AddAxiom addAxiom2 = new AddAxiom(programModel, subClassAxiom2);

                manager.applyChange(addAxiom2);


                /* Data Properties */
                OWLDataProperty dataPropertyBolumAdi = factory.getOWLDataProperty(":bolum_adi", pm);
                OWLDataProperty dataPropertyBolumNumarasi = factory.getOWLDataProperty(":bolum_numarasi", pm);
                OWLDataProperty dataPropertyIsim = factory.getOWLDataProperty(":isim", pm);
                OWLDataProperty dataPropertySezonNumarasi = factory.getOWLDataProperty(":sezon_numarasi", pm);
                OWLDataProperty dataPropertySoyisim = factory.getOWLDataProperty(":soyisim", pm);

                /* Object Properties */
                OWLObjectProperty objectPropertyIcerir = factory.getOWLObjectProperty(":icerir", pm);
                OWLObjectProperty objectPropertyOynar = factory.getOWLObjectProperty(":oynar", pm);
                OWLObjectProperty objectPropertyYonetir = factory.getOWLObjectProperty(":yonetir", pm);

                /* Individuals */

                OWLNamedIndividual namedIndividualBolum3 = factory.getOWLNamedIndividual(":Bolum3", pm);
                OWLNamedIndividual namedIndividualBolum21 = factory.getOWLNamedIndividual(":Bolum21", pm);

                OWLNamedIndividual namedIndividualOyuncu3 = factory.getOWLNamedIndividual(":Oyuncu3", pm);

                OWLNamedIndividual namedIndividualSezon2 = factory.getOWLNamedIndividual(":Sezon2", pm);

                OWLNamedIndividual namedIndividualYardimciYonetmen = factory.getOWLNamedIndividual(":YardimciYonetmen1", pm);


                Set<OWLAxiom> axiomSet = new HashSet<OWLAxiom>();

                axiomSet.add(factory.getOWLDataPropertyAssertionAxiom(dataPropertyBolumAdi,namedIndividualBolum3, "Bolum 3" ));
                axiomSet.add(factory.getOWLDataPropertyAssertionAxiom(dataPropertyBolumNumarasi, namedIndividualBolum3, "3"));
                axiomSet.add(factory.getOWLDataPropertyAssertionAxiom(dataPropertySezonNumarasi, namedIndividualBolum3, "1"));

                axiomSet.add(factory.getOWLDataPropertyAssertionAxiom(dataPropertySezonNumarasi,namedIndividualSezon2,"2" ));

                axiomSet.add(factory.getOWLDataPropertyAssertionAxiom(dataPropertyBolumAdi,namedIndividualBolum21,"Salvation"));
                axiomSet.add(factory.getOWLDataPropertyAssertionAxiom(dataPropertyBolumNumarasi, namedIndividualBolum21, "21"));
                axiomSet.add(factory.getOWLDataPropertyAssertionAxiom(dataPropertySezonNumarasi, namedIndividualBolum21, "2"));

                axiomSet.add(factory.getOWLDataPropertyAssertionAxiom(dataPropertyIsim, namedIndividualOyuncu3, "Gamble" ));
                axiomSet.add(factory.getOWLDataPropertyAssertionAxiom(dataPropertySoyisim, namedIndividualOyuncu3, "Manning" ));


                axiomSet.add(factory.getOWLDataPropertyAssertionAxiom(dataPropertyIsim, namedIndividualYardimciYonetmen, "Kim"));
                axiomSet.add(factory.getOWLDataPropertyAssertionAxiom(dataPropertySoyisim, namedIndividualYardimciYonetmen, "Manner"));

                axiomSet.add(factory.getOWLObjectPropertyAssertionAxiom(objectPropertyYonetir, namedIndividualYardimciYonetmen, namedIndividualSezon2));




                manager.addAxioms(programModel, axiomSet);
               // File ontologyFileNew = new File("C:\\Users\\nesli\\Desktop\\Ontology\\ontologySource\\ProgramDomainRdfTypeNew.owl");
               // OWLOntology programModelNew = manager.loadOntologyFromOntologyDocument(ontologyFileNew);
               // manager.addAxioms(programModelNew, programModel.getAxioms());
                try{
                    manager.saveOntology(programModel);
                    System.out.println("Kural ekleme tamamlandı.");
                } catch (OWLOntologyStorageException ex){
                    ex.printStackTrace();
                }


            }catch (Exception exception){
                exception.printStackTrace();
            }

            OWLReasonerFactory reasonerFactory = new StructuralReasonerFactory();
            OWLReasoner reasoner = reasonerFactory.createReasoner(programModel);
            if (reasoner.isConsistent()){
                System.out.println("Reasoner olusturuldu");
            } else System.out.println("Reasoner ERROR !");

            List<InferredAxiomGenerator<? extends OWLAxiom>> generators = new ArrayList<>();
            generators.add(new InferredSubClassAxiomGenerator());
            generators.add(new InferredClassAssertionAxiomGenerator());


            InferredOntologyGenerator ontologyGenerator = new InferredOntologyGenerator(reasoner, generators);
            OWLOntology inferredOntology = manager.createOntology();
            ontologyGenerator.fillOntology(manager, inferredOntology);

            File inferredOntologyFile = new File("C:\\Users\\nesli\\Desktop\\Ontology\\ontologySource\\InferredOntology.owl");
            if(inferredOntologyFile.exists() == false){
                inferredOntologyFile.createNewFile();
            }
            inferredOntologyFile = inferredOntologyFile.getAbsoluteFile();
        OutputStream outputStream = new FileOutputStream(inferredOntologyFile);
        manager.saveOntology(inferredOntology, manager.getOntologyFormat(programModel), outputStream);

        OWLOntologyMerger ontologyMerger = new OWLOntologyMerger(manager);
        IRI mergedOntologyIRI = IRI.create("http://www.semanticweb.org/neslihan/ontologies/2021/5/anlamsalagodevi/#");
        OWLOntology merged = ontologyMerger.createMergedOntology(manager, mergedOntologyIRI);

        File mergedinferredOntologyFile =  new File("C:\\Users\\nesli\\Desktop\\Ontology\\ontologySource\\MergedInferredOntology.owl");
        if(mergedinferredOntologyFile.exists() == false){
            mergedinferredOntologyFile.createNewFile();
        }
        mergedinferredOntologyFile = mergedinferredOntologyFile.getAbsoluteFile();
        OutputStream outputStream1 = new FileOutputStream(mergedinferredOntologyFile);
        manager.saveOntology(merged, manager.getOntologyFormat(programModel), outputStream1);


    }




}
