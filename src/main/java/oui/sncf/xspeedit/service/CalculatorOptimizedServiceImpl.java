package oui.sncf.xspeedit.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import oui.sncf.xspeedit.exception.XspeedItException;

import java.util.*;

@Service("calculatorOptimizedService")
public class CalculatorOptimizedServiceImpl implements CalculatorService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Override
    public String organizeTheBoxes(String items, String itemsRegexDelimiter, String boxesDelimiter, Integer boxesCapacity) throws XspeedItException {
        LOGGER.debug("Organisation des boxes pour les articles suivants : " + items);

        String boxes = "";

        // Vérification préalable au traitement
        if(items == null || "".equals(items)) {
            return "";
        }

        // Ajout des articles dans une Map triée de type <item, recurrence>
        Map<Integer, Integer> itemMap = new TreeMap<>(Collections.reverseOrder());
        for(String letter : items.split(itemsRegexDelimiter)) {
            try { // Vérification que les articles soient bien sous la forme d'une liste d'entiers
                Integer item = Integer.parseInt(letter);
                // Vérification que l'article n'est pas plus gros que la capacité d'un carton
                if(item > boxesCapacity) {
                    throw new XspeedItException("Un article ne passe pas dans un carton, même vide.");
                }

                // Ajout de l'article dans itemMap (en incrémentant les récurrences)
                itemMap.merge(item, 1, Integer::sum);
            } catch(NumberFormatException e) {
                throw new XspeedItException("Les articles ne sont pas au bon format.", e.getStackTrace());
            }
        }

        // Parcourt de la Map ...
        for(Map.Entry<Integer, Integer> entry : itemMap.entrySet()) {
            // ... sans oublier les récurrences
            while(entry.getValue() > 0) {
                // Nouveau carton
                Integer remainingCapacity = boxesCapacity - entry.getKey();
                boxes += "".equals(boxes) ? "" : boxesDelimiter;

                // Ajout de l'article dans le carton courant
                itemMap.merge(entry.getKey(), -1, Integer::sum);
                boxes += entry.getKey();

                // Tentative de trouver des articles pouvant s'ajouter à celui-ci
                for(Map.Entry<Integer, Integer> entry2 : itemMap.entrySet()) {
                    Boolean keepGoing = Boolean.TRUE;
                    do { // Boucler sur les récurrences
                        if(entry2.getValue() > 0 && remainingCapacity >= entry2.getKey()) {
                            // Ajout de l'article dans le carton courant
                            remainingCapacity -= entry2.getKey();
                            itemMap.merge(entry2.getKey(), -1, Integer::sum);
                            boxes += entry2.getKey();

                            // Vérification que le carton n'est pas plein
                            if(remainingCapacity == 0) {
                                break;
                            }
                        } else {
                            // Si l'article n'a pas la capacité pour entrer dans le carton, on ne continue pas pour ses récurrences
                            keepGoing = Boolean.FALSE;
                        }
                    } while (keepGoing);
                }
            }
        }
        return boxes;
    }
}
