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
package mage.sets.magic2015;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.PutCountersSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.counters.Counter;
import mage.filter.Filter;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author LevelX2
 */
public class YisanTheWandererBard extends CardImpl {

    public YisanTheWandererBard(UUID ownerId) {
        super(ownerId, 209, "Yisan, the Wanderer Bard", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{G}");
        this.expansionSetCode = "M15";
        this.supertype.add("Legendary");
        this.subtype.add("Human");
        this.subtype.add("Rogue");

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // {2}{G}, {T}, Put a verse counter on Yisan, the Wanderer Bard: Search your library for a creature card with converted mana cost equal to the number of verse counters on Yisan, put it onto the battlefield, then shuffle your library.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new YisanTheWandererBardEffect(), new ManaCostsImpl("{2}{G}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new PutCountersSourceCost(new Counter("verse")));
        this.addAbility(ability);
    }

    public YisanTheWandererBard(final YisanTheWandererBard card) {
        super(card);
    }

    @Override
    public YisanTheWandererBard copy() {
        return new YisanTheWandererBard(this);
    }
}

class YisanTheWandererBardEffect extends OneShotEffect {
    
    public YisanTheWandererBardEffect() {
        super(Outcome.Benefit);
        this.staticText = "Search your library for a creature card with converted mana cost equal to the number of verse counters on {this}, put it onto the battlefield, then shuffle your library";
    }
    
    public YisanTheWandererBardEffect(final YisanTheWandererBardEffect effect) {
        super(effect);
    }
    
    @Override
    public YisanTheWandererBardEffect copy() {
        return new YisanTheWandererBardEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (sourcePermanent != null && player != null) {
            int newConvertedCost = sourcePermanent.getCounters().getCount("verse");
            FilterCard filter = new FilterCard("creature card with converted mana cost " + newConvertedCost);
            filter.add(new ConvertedManaCostPredicate(Filter.ComparisonType.Equal, newConvertedCost));
            filter.add(new CardTypePredicate(CardType.CREATURE));
            TargetCardInLibrary target = new TargetCardInLibrary(filter);
            if (player.searchLibrary(target, game)) {
                for (UUID cardId : target.getTargets()) {
                    Card card = player.getLibrary().getCard(cardId, game);
                    if (card != null) {
                        player.putOntoBattlefieldWithInfo(card, game, Zone.LIBRARY, source.getSourceId());
                    }
                }
            }
            player.shuffleLibrary(game);
            return true;
        }
        return false;
    }
}
