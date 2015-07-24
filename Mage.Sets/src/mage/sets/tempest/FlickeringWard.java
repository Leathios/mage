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
package mage.sets.tempest;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.choices.ChoiceColor;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public class FlickeringWard extends CardImpl {

    public FlickeringWard(UUID ownerId) {
        super(ownerId, 231, "Flickering Ward", Rarity.UNCOMMON, new CardType[]{CardType.ENCHANTMENT}, "{W}");
        this.expansionSetCode = "TMP";
        this.subtype.add("Aura");

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Protect));
        this.addAbility(new EnchantAbility(auraTarget.getTargetName()));
        
        // As Flickering Ward enters the battlefield, choose a color.
        // Enchanted creature has protection from the chosen color. This effect doesn't remove Flickering Ward.
        this.addAbility(new AsEntersBattlefieldAbility(new FlickeringWardEffect()));
        
        // {W}: Return Flickering Ward to its owner's hand.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new ReturnToHandSourceEffect(), new ManaCostsImpl("{W}")));
    }

    public FlickeringWard(final FlickeringWard card) {
        super(card);
    }

    @Override
    public FlickeringWard copy() {
        return new FlickeringWard(this);
    }
}

class FlickeringWardEffect extends OneShotEffect {
    
    public FlickeringWardEffect() {
        super(Outcome.Protect);
        this.staticText = "enchanted creature has protection from the chosen color. This effect doesn't remove {this}";
    }
    
    public FlickeringWardEffect(final FlickeringWardEffect effect) {
        super(effect);
    }
    
    @Override
    public FlickeringWardEffect copy() {
        return new FlickeringWardEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        ChoiceColor choice = new ChoiceColor();
        choice.setMessage("Choose color to get protection from");
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && controller.choose(outcome, choice, game)) {
            FilterCard protectionFilter = new FilterCard();
            protectionFilter.add(new ColorPredicate(choice.getColor()));
            protectionFilter.setMessage(choice.getChoice().toLowerCase());
            ProtectionAbility protectionAbility = new ProtectionAbility(protectionFilter);
            protectionAbility.setRemovesAuras(false);
            ContinuousEffect effect = new GainAbilityAttachedEffect(protectionAbility, AttachmentType.AURA, Duration.WhileOnBattlefield);
            game.addEffect(effect, source);
            return true;
        }
        return false;
    }
}
