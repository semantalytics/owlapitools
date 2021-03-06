/**
 * Date: Dec 17, 2007
 *
 * code made available under Mozilla Public License (http://www.mozilla.org/MPL/MPL-1.1.html)
 *
 * copyright 2007, The University of Manchester
 *
 * @author Nick Drummond, The University Of Manchester, Bio Health Informatics Group
 */
package org.coode.suggestor.impl;

import org.coode.suggestor.api.PropertySanctionRule;
import org.coode.suggestor.api.PropertySuggestor;
import org.coode.suggestor.util.RestrictionAccumulator;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

/** Check the restrictions on the class for min cardi zeros */
public class MinCardinalitySanctionRule implements PropertySanctionRule {

    private OWLReasoner r;

    @Override
    public void setSuggestor(PropertySuggestor ps) {
        r = ps.getReasoner();
    }

    @Override
    public <T extends OWLPropertyExpression> boolean meetsSanction(OWLClassExpression c, T p) {
        RestrictionAccumulator acc = new RestrictionAccumulator(r);
        Class<? extends OWLRestriction> class1 = p.isOWLDataProperty() ? OWLDataMinCardinality.class
            : OWLObjectMinCardinality.class;
        return acc.accummulateRestrictions(c, p, class1)
            .anyMatch(restr -> ((OWLCardinalityRestriction<?>) restr).getCardinality() == 0);
    }
}
