/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage.sets.avacynrestored;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.PutLibraryIntoGraveTargetEffect;
import mage.cards.CardImpl;
import mage.filter.common.FilterLandPermanent;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 *
 * @author noxx

 */
public class Dreadwaters extends CardImpl<Dreadwaters> {

    private static final FilterLandPermanent filter = new FilterLandPermanent("lands you control");

    static {
        filter.setTargetController(Constants.TargetController.YOU);
    }

    public Dreadwaters(UUID ownerId) {
        super(ownerId, 49, "Dreadwaters", Rarity.COMMON, new CardType[]{CardType.SORCERY}, "{3}{U}");
        this.expansionSetCode = "AVR";

        this.color.setBlue(true);

        // Target player puts the top X cards of his or her library into his or her graveyard, where X is the number of lands you control.
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new PutLibraryIntoGraveTargetEffect(new PermanentsOnBattlefieldCount(filter)));
    }

    public Dreadwaters(final Dreadwaters card) {
        super(card);
    }

    @Override
    public Dreadwaters copy() {
        return new Dreadwaters(this);
    }
}
