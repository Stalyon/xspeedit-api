package oui.sncf.xspeedit.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import oui.sncf.xspeedit.exception.XspeedItException;

@Service("calculatorActualService")
public class CalculatorActualServiceImpl implements CalculatorService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Override
    public String organizeTheBoxes(String items, String itemsRegexDelimiter, String boxesDelimiter, Integer boxesCapacity)
            throws XspeedItException {
        LOGGER.debug("Organisation des boxes pour les articles suivants :" + items);

        String boxes = "";

        // Vérification préalable au traitement
        if(items == null || "".equals(items)) {
            return "";
        }

        // Parcourt de tous les items
        Integer remainingCapacity = boxesCapacity;
        for(String letter : items.split(itemsRegexDelimiter)) {
            Integer item;
            try { // Vérification que les articles sont bien sous la forme d'une liste d'entiers
                item = Integer.parseInt(letter);
            } catch(NumberFormatException e) {
                throw new XspeedItException("Les articles ne sont pas au bon format.", e.getStackTrace());
            }

            if(item > boxesCapacity) { // Vérification que l'article n'est pas plus gros que la capacité d'un carton
                throw new XspeedItException("Un article ne passe pas dans un carton, même vide.");
            } else if(remainingCapacity < item) { // Vérification qu'il reste de la place dans le carton
                // Nouveau carton
                remainingCapacity = boxesCapacity - item;
                boxes += boxesDelimiter;
            } else {
                // Mise à jour de la capacité si le nouvel article est ajouté dans le carton
                remainingCapacity -= item;
            }
            // Ajout de l'article dans le carton courant
            boxes += letter;
        }
        return boxes;
    }
}
